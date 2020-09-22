package com.changgou.content.feign;

import com.changgou.goods.pojo.Content
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
@RequestMapping("/content")
public interface ContentFeign {

    /**
     * Content 分页条件搜索实现
     * @param content
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) Content content, @PathVariable int page, @PathVariable int size);

    /**
     * Content 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /**
     * 多条件搜索品牌数据
     * @param content
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Content>> findList(@RequestBody(required = false) Content content);

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /**
     * 修改 Content数据
     * @param content
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Content content, @PathVariable Long id);

    /**
     * 新增 Content 数据
     * @param content
     * @return
     */
    @PostMapping
    Result add(@RequestBody Content content);

    /**
     * 根据 ID 查询 Content 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Content> findById(@PathVariable Long id);

    /**
     * 查询 Content 全部数据
     * @return
     */
    @GetMapping
    Result<List<Content>> findAll();
}