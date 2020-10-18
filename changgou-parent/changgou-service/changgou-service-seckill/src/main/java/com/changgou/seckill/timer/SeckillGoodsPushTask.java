package com.changgou.seckill.timer;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import entity.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Dylan Guo
 * @date 10/17/2020 09:23
 * @description 定时将秒杀商品存入 redis 缓存
 */
@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时执行
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodsPushRedis() {
        /**
         * 1. 查询符合当前参与秒杀的时间菜单
         * 2. 秒杀商品库存 > 0
         * 3. 审核状态->审核通过
         * 4. 时间菜单的开始时间 <= start_time && end_time < 时间菜单的开始时间 + 2小时
         */

        // 求时间菜单
        List<Date> dateMenus = DateUtil.getDateMenus();

        // 循环查询每个时间区间的秒杀商品
        for (Date dateMenu : dateMenus) {
            // 时间的字符串格式 yyyyMMddHH
            String timespace = "SeckillGoods_" + DateUtil.data2str(dateMenu, "yyyyMMddHH");

            // 审核状态->审核通过
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            // status=1
            criteria.andEqualTo("status", "1");
            // 秒杀商品库存 > 0
            criteria.andGreaterThan("stockCount", 0);
            // 时间菜单的开始时间 <= start_time && end_time < 时间菜单的开始时间 + 2小时
            criteria.andGreaterThanOrEqualTo("startTime", dateMenu);
            criteria.andLessThan("endTime", DateUtil.addDateHour(dateMenu, 2));

            // 排除已经存到 redis 中的数据
            Set keys = redisTemplate.boundHashOps(timespace).keys();
            if (keys != null && keys.size() > 0) {
                criteria.andNotIn("id", keys);
            }

            // 查询数据
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);

            // 存入到 redis
            for (SeckillGoods seckillGood : seckillGoods) {
                System.out.println("商品ID：" + seckillGood.getId() + "---存入到 Redis---" + timespace);
                redisTemplate.boundHashOps(timespace).put(seckillGood.getId(), seckillGood);

                // 给每个商品做个队列
                redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillGood.getId()).leftPushAll(putAllIds(seckillGood.getStockCount(), seckillGood.getId()));
            }
        }
    }


    /**
     * 获取每个商品的ID集合
     *
     * @param number
     * @param id
     * @return
     */
    public Long[] putAllIds(Integer number, Long id) {
        Long[] ids = new Long[number];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = id;
        }
        return ids;
    }
}
