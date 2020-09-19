package com.changgou.goods.controller;

import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
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
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 根据分类 ID 查询对应的规格列表
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Spec>> findByCategoryId(@PathVariable("id") Integer categoryId) {
        List<Spec> specs = specService.findByCategoryId(categoryId);
        return new Result<List<Spec>>(true, StatusCode.OK, "查询成功", specs);
    }

    /**
     * Spec 分页条件搜索实现
     *
     * @param spec
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Spec spec, @PathVariable int page, @PathVariable int size) {
        // 调用 SpecService 实现分页条件查询 Spec
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Spec 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 SpecService 实现分页查询 Spec
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param spec
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spec>> findList(@RequestBody(required = false) Spec spec) {
        //调用 SpecService 实现条件查询 Spec
        List<Spec> list = specService.findList(spec);
        return new Result<List<Spec>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 SpecService 实现根据主键删除
        specService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Spec数据
     *
     * @param spec
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spec spec, @PathVariable Integer id) {
        // 设置主键值
        spec.setId(id);
        // 调用 SpecService 实现修改 Spec
        specService.update(spec);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Spec 数据
     *
     * @param spec
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spec spec) {
        // 调用 SpecService 实现添加 Spec
        specService.add(spec);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Spec 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable Integer id) {
        // 调用 SpecService 实现根据主键查询 Spec
        Spec spec = specService.findById(id);
        return new Result<Spec>(true, StatusCode.OK, "ID 查询成功!", spec);
    }

    /**
     * 查询 Spec 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Spec>> findAll() {
        // 调用 SpecService 实现查询所有 Spec
        List<Spec> list = specService.findAll();
        return new Result<List<Spec>>(true, StatusCode.OK, "查询成功!", list);
    }
}
