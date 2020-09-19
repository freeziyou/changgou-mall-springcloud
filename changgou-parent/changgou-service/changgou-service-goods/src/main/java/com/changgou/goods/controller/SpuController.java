package com.changgou.goods.controller;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/spu")
@CrossOrigin
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 恢复数据
     *
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable Long id) {
        spuService.restore(id);
        return new Result(true, StatusCode.OK, "数据恢复成功!");
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/logic/delete/{id}")
    public Result logicDelete(@PathVariable Long id) {
        spuService.logicDelete(id);
        return new Result(true, StatusCode.OK, "逻辑删除成功!");
    }

    /**
     * 批量下架
     *
     * @param ids
     * @return
     */
    @PutMapping("/pull/many")
    public Result pullMany(@RequestBody Long[] ids) {
        int count = spuService.pullMany(ids);
        return new Result(true, StatusCode.OK, "下架" + count + "个商品!");
    }

    /**
     * 批量上架
     *
     * @param ids
     * @return
     */
    @PutMapping("/put/many")
    public Result putMany(@RequestBody Long[] ids) {
        int count = spuService.putMany(ids);
        return new Result(true, StatusCode.OK, "上架" + count + "个商品!");
    }

    /**
     * 商品上架
     *
     * @param id
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable Long id) {
        spuService.put(id);
        return new Result(true, StatusCode.OK, "上架成功!");
    }

    /**
     * 下架
     *
     * @param id
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable Long id) {
        spuService.pull(id);
        return new Result(true, StatusCode.OK, "下架成功!");
    }

    /**
     * 审核上架
     *
     * @param id
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable Long id) {
        spuService.audit(id);
        return new Result(true, StatusCode.OK, "审核成功!");
    }

    /**
     * 根据 ID 查询 Goods
     *
     * @param spuId
     * @return
     */
    @GetMapping("/goods/{id}")
    public Result<Goods> findGoodsById(@PathVariable("id") Long spuId) {
        Goods goods = spuService.findGoodsById(spuId);
        return new Result<Goods>(true, StatusCode.OK, "查询成功!", goods);
    }

    /**
     * 增加商品
     *
     * @param goods 商品
     * @return
     */
    @PostMapping("/save")
    public Result saveGoods(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return new Result(true, StatusCode.OK, "商品增加成功!");
    }

    /**
     * Spu 分页条件搜索实现
     *
     * @param spu
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Spu spu, @PathVariable int page, @PathVariable int size) {
        // 调用 SpuService 实现分页条件查询 Spu
        PageInfo<Spu> pageInfo = spuService.findPage(spu, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Spu 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 SpuService 实现分页查询 Spu
        PageInfo<Spu> pageInfo = spuService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param spu
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spu>> findList(@RequestBody(required = false) Spu spu) {
        //调用 SpuService 实现条件查询 Spu
        List<Spu> list = spuService.findList(spu);
        return new Result<List<Spu>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 SpuService 实现根据主键删除
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Spu数据
     *
     * @param spu
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spu spu, @PathVariable Long id) {
        // 设置主键值
        spu.setId(id);
        // 调用 SpuService 实现修改 Spu
        spuService.update(spu);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Spu 数据
     *
     * @param spu
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spu spu) {
        // 调用 SpuService 实现添加 Spu
        spuService.add(spu);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Spu 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Spu> findById(@PathVariable Long id) {
        // 调用 SpuService 实现根据主键查询 Spu
        Spu spu = spuService.findById(id);
        return new Result<Spu>(true, StatusCode.OK, "ID 查询成功!", spu);
    }

    /**
     * 查询 Spu 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Spu>> findAll() {
        // 调用 SpuService 实现查询所有 Spu
        List<Spu> list = spuService.findAll();
        return new Result<List<Spu>>(true, StatusCode.OK, "查询成功!", list);
    }
}
