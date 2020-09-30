package com.changgou.user.controller;

import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
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
@RequestMapping("/areas")
@CrossOrigin
public class AreasController {

    @Autowired
    private AreasService areasService;

    /**
     * Areas 分页条件搜索实现
     *
     * @param areas
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Areas areas, @PathVariable int page, @PathVariable int size) {
        // 调用 AreasService 实现分页条件查询 Areas
        PageInfo<Areas> pageInfo = areasService.findPage(areas, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Areas 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 AreasService 实现分页查询 Areas
        PageInfo<Areas> pageInfo = areasService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param areas
     * @return
     */
    @PostMapping("/search")
    public Result<List<Areas>> findList(@RequestBody(required = false) Areas areas) {
        //调用 AreasService 实现条件查询 Areas
        List<Areas> list = areasService.findList(areas);
        return new Result<List<Areas>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 AreasService 实现根据主键删除
        areasService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Areas数据
     *
     * @param areas
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Areas areas, @PathVariable String id) {
        // 设置主键值
        areas.setAreaid(id);
        // 调用 AreasService 实现修改 Areas
        areasService.update(areas);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Areas 数据
     *
     * @param areas
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Areas areas) {
        // 调用 AreasService 实现添加 Areas
        areasService.add(areas);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Areas 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Areas> findById(@PathVariable String id) {
        // 调用 AreasService 实现根据主键查询 Areas
        Areas areas = areasService.findById(id);
        return new Result<Areas>(true, StatusCode.OK, "ID 查询成功!", areas);
    }

    /**
     * 查询 Areas 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Areas>> findAll() {
        // 调用 AreasService 实现查询所有 Areas
        List<Areas> list = areasService.findAll();
        return new Result<List<Areas>>(true, StatusCode.OK, "查询成功!", list);
    }
}
