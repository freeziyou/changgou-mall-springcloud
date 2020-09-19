package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
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
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * Sku 分页条件搜索实现
     *
     * @param sku
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Sku sku, @PathVariable int page, @PathVariable int size);

    /**
     * Sku 分页搜索实现
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
     * @param sku
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Sku>> findList(@RequestBody(required = false) Sku sku);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Long id);

    /**
     * 修改 Sku数据
     *
     * @param sku
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Sku sku, @PathVariable Long id);

    /**
     * 新增 Sku 数据
     *
     * @param sku
     * @return
     */
    @PostMapping
    Result add(@RequestBody Sku sku);

    /**
     * 根据 ID 查询 Sku 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Sku> findById(@PathVariable Long id);

    /**
     * 查询 Sku 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Sku>> findAll();
}