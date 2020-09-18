package com.changgou.controller;

import com.changgou.goods.pojo.Spec;
import com.changgou.service.SpecService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/18/2020 13:57
 * @description TODO
 */
@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * Spec 分页条件搜索实现
     *
     * @param spec 查询条件
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Spec spec, @PathVariable int page, @PathVariable int size) {
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Spec 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param spec 查询条件
     * @return 查询结果
     */
    @PostMapping("/search")
    public Result<List<Spec>> findList(@RequestBody(required = false) Spec spec) {
        List<Spec> list = specService.findList(spec);
        return new Result<List<Spec>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id id
     * @return 消息结果
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        specService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Spec 数据
     *
     * @param spec 规格
     * @param id   id
     * @return 消息结果
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spec spec, @PathVariable Integer id) {
        //设置主键值
        spec.setId(id);
        //修改数据
        specService.update(spec);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Spec 数据
     *
     * @param spec 规格
     * @return 消息
     */
    @PostMapping
    public Result add(@RequestBody Spec spec) {
        specService.add(spec);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Spec 数据
     *
     * @param id id
     */
    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable Integer id) {
        Spec spec = specService.findById(id);
        return new Result<Spec>(true, StatusCode.OK, "id 查询成功!", spec);
    }

    /**
     * 查询 Spec 全部数据
     *
     * @return Spec 全部数据
     */
    @GetMapping
    public Result<List<Spec>> findAll() {
        List<Spec> list = specService.findAll();
        return new Result<List<Spec>>(true, StatusCode.OK, "查询成功!", list);
    }
}
