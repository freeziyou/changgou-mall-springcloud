package com.changgou.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.xpand.starter.canal.annotation.*;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/21/2020 12:50
 * @description 实现对表增删改操作的监听
 */
@CanalEventListener
public class CanalDataEventListener {

    @Autowired
    private ContentFeign contentFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 监听自定义数据库的操作, 增删改
     *
     * @param eventType
     * @param rowData
     */
    @ListenPoint(destination = "example",
            schema = "changgou_content",
            table = {"tb_content", "tb_content_category"},
            eventType = {
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT}
    )
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1. 获取列名为 category_id 的值
        String categoryId = getColumnValue(eventType, rowData);

        //2. 调用 feign 获取该分类下的所有的广告集合
        Result<List<Content>> categoryList = contentFeign.findByCategory(Long.valueOf(categoryId));
        List<Content> data = categoryList.getData();

        //3. 使用 redisTemplate 存储到 redis 中
        stringRedisTemplate.boundValueOps("content_" + categoryId).set(JSON.toJSONString(data));
    }

    private String getColumnValue(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String categoryId = "";

        // 判断如果是删除, 则获取 beforeList
        if (eventType == CanalEntry.EventType.DELETE) {
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if ("category_id".equalsIgnoreCase(column.getName())) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        } else {
            // 判断如果是添加或者是更新, 获取 afterList
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if ("category_id".equalsIgnoreCase(column.getName())) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }
        return categoryId;
    }
}