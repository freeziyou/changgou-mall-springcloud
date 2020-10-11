package com.changgou.goods.controller;

import com.changgou.goods.pojo.Sku;
import com.changgou.goods.service.SkuService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/sku")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 商品信息递减
     *
     * @param decrMap
     * @return
     */
    @GetMapping("/decr/count")
    public Result decrCount(@RequestParam Map<String, Integer> decrMap) {

        skuService.decrCount(decrMap);
        return new Result(true, StatusCode.OK, "商品库存递减成功!");
    }

    /**
     * 根据审核状态查询 Sku
     *
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    public Result<List<Sku>> findByStatus(@PathVariable String status) {
        List<Sku> list = skuService.findByStatus(status);
        return new Result<List<Sku>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * Sku 分页条件搜索实现
     *
     * @param sku
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Sku sku, @PathVariable int page, @PathVariable int size) {
        // 调用 SkuService 实现分页条件查询 Sku
        PageInfo<Sku> pageInfo = skuService.findPage(sku, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Sku 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 SkuService 实现分页查询 Sku
        PageInfo<Sku> pageInfo = skuService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param sku
     * @return
     */
    @PostMapping("/search")
    public Result<List<Sku>> findList(@RequestBody(required = false) Sku sku) {
        //调用 SkuService 实现条件查询 Sku
        List<Sku> list = skuService.findList(sku);
        return new Result<List<Sku>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 SkuService 实现根据主键删除
        skuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Sku数据
     *
     * @param sku
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Sku sku, @PathVariable Long id) {
        // 设置主键值
        sku.setId(id);
        // 调用 SkuService 实现修改 Sku
        skuService.update(sku);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Sku 数据
     *
     * @param sku
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Sku sku) {
        // 调用 SkuService 实现添加 Sku
        skuService.add(sku);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Sku 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable Long id) {
        // 调用 SkuService 实现根据主键查询 Sku
        Sku sku = skuService.findById(id);
        return new Result<Sku>(true, StatusCode.OK, "ID 查询成功!", sku);
    }

    /**
     * 查询 Sku 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Sku>> findAll() {
        // 调用 SkuService 实现查询所有 Sku
        List<Sku> list = skuService.findAll();
        return new Result<List<Sku>>(true, StatusCode.OK, "查询成功!", list);
    }
}
