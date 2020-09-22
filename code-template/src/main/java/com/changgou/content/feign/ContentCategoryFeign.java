package com.changgou.content.feign;

import com.changgou.goods.pojo.ContentCategory
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@FeignClient(name="content")
@RequestMapping("/contentCategory")
public interface ContentCategoryFeign {

    /**
     * ContentCategory 分页条件搜索实现
     * @param contentCategory
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) ContentCategory contentCategory, @PathVariable int page, @PathVariable int size);

    /**
     * ContentCategory 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /**
     * 多条件搜索品牌数据
     * @param contentCategory
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<ContentCategory>> findList(@RequestBody(required = false) ContentCategory contentCategory);

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /**
     * 修改 ContentCategory数据
     * @param contentCategory
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody ContentCategory contentCategory, @PathVariable Long id);

    /**
     * 新增 ContentCategory 数据
     * @param contentCategory
     * @return
     */
    @PostMapping
    Result add(@RequestBody ContentCategory contentCategory);

    /**
     * 根据 ID 查询 ContentCategory 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<ContentCategory> findById(@PathVariable Long id);

    /**
     * 查询 ContentCategory 全部数据
     * @return
     */
    @GetMapping
    Result<List<ContentCategory>> findAll();
}