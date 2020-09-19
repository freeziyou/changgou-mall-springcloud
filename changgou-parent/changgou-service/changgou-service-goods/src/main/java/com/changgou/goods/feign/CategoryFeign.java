package com.changgou.goods.feign;

import com.changgou.goods.pojo.Category;
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
@FeignClient(name = "goods")
@RequestMapping("/category")
public interface CategoryFeign {

    /**
     * Category 分页条件搜索实现
     *
     * @param category
     * @param page     当前页
     * @param size     每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Category category, @PathVariable int page, @PathVariable int size);

    /**
     * Category 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /**
     * 多条件搜索品牌数据
     *
     * @param category
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Category>> findList(@RequestBody(required = false) Category category);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Category数据
     *
     * @param category
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Category category, @PathVariable Integer id);

    /**
     * 新增 Category 数据
     *
     * @param category
     * @return
     */
    @PostMapping
    Result add(@RequestBody Category category);

    /**
     * 根据 ID 查询 Category 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Category> findById(@PathVariable Integer id);

    /**
     * 查询 Category 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Category>> findAll();
}