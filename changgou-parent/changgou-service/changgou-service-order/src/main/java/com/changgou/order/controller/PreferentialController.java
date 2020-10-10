package com.changgou.order.controller;

import com.changgou.order.pojo.Preferential;
import com.changgou.order.service.PreferentialService;
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
@RequestMapping("/preferential")
@CrossOrigin
public class PreferentialController {

    @Autowired
    private PreferentialService preferentialService;

    /**
     * Preferential 分页条件搜索实现
     *
     * @param preferential
     * @param page         当前页
     * @param size         每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Preferential preferential, @PathVariable int page, @PathVariable int size) {
        // 调用 PreferentialService 实现分页条件查询 Preferential
        PageInfo<Preferential> pageInfo = preferentialService.findPage(preferential, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Preferential 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 PreferentialService 实现分页查询 Preferential
        PageInfo<Preferential> pageInfo = preferentialService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param preferential
     * @return
     */
    @PostMapping("/search")
    public Result<List<Preferential>> findList(@RequestBody(required = false) Preferential preferential) {
        //调用 PreferentialService 实现条件查询 Preferential
        List<Preferential> list = preferentialService.findList(preferential);
        return new Result<List<Preferential>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 PreferentialService 实现根据主键删除
        preferentialService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Preferential数据
     *
     * @param preferential
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Preferential preferential, @PathVariable Integer id) {
        // 设置主键值
        preferential.setId(id);
        // 调用 PreferentialService 实现修改 Preferential
        preferentialService.update(preferential);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Preferential 数据
     *
     * @param preferential
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Preferential preferential) {
        // 调用 PreferentialService 实现添加 Preferential
        preferentialService.add(preferential);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Preferential 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Preferential> findById(@PathVariable Integer id) {
        // 调用 PreferentialService 实现根据主键查询 Preferential
        Preferential preferential = preferentialService.findById(id);
        return new Result<Preferential>(true, StatusCode.OK, "ID 查询成功!", preferential);
    }

    /**
     * 查询 Preferential 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Preferential>> findAll() {
        // 调用 PreferentialService 实现查询所有 Preferential
        List<Preferential> list = preferentialService.findAll();
        return new Result<List<Preferential>>(true, StatusCode.OK, "查询成功!", list);
    }
}
