package com.changgou.goods.feign;

import com.changgou.goods.pojo.CategoryBrand;
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
@RequestMapping("/categoryBrand")
public interface CategoryBrandFeign {

    /**
     * CategoryBrand 分页条件搜索实现
     *
     * @param categoryBrand
     * @param page          当前页
     * @param size          每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) CategoryBrand categoryBrand, @PathVariable int page, @PathVariable int size);

    /**
     * CategoryBrand 分页搜索实现
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
     * @param categoryBrand
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<CategoryBrand>> findList(@RequestBody(required = false) CategoryBrand categoryBrand);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 CategoryBrand数据
     *
     * @param categoryBrand
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody CategoryBrand categoryBrand, @PathVariable Integer id);

    /**
     * 新增 CategoryBrand 数据
     *
     * @param categoryBrand
     * @return
     */
    @PostMapping
    Result add(@RequestBody CategoryBrand categoryBrand);

    /**
     * 根据 ID 查询 CategoryBrand 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<CategoryBrand> findById(@PathVariable Integer id);

    /**
     * 查询 CategoryBrand 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<CategoryBrand>> findAll();
}