package com.changgou.user.controller;

import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
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
@RequestMapping("/provinces")
@CrossOrigin
public class ProvincesController {

    @Autowired
    private ProvincesService provincesService;

    /**
     * Provinces 分页条件搜索实现
     * @param provinces
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Provinces provinces, @PathVariable int page, @PathVariable int size) {
        // 调用 ProvincesService 实现分页条件查询 Provinces
        PageInfo<Provinces> pageInfo = provincesService.findPage(provinces, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Provinces 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ProvincesService 实现分页查询 Provinces
        PageInfo<Provinces> pageInfo = provincesService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     * @param provinces
     * @return
     */
    @PostMapping("/search")
    public Result<List<Provinces>> findList(@RequestBody(required = false) Provinces provinces) {
        //调用 ProvincesService 实现条件查询 Provinces
        List<Provinces> list = provincesService.findList(provinces);
        return new Result<List<Provinces>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 ProvincesService 实现根据主键删除
        provincesService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Provinces数据
     * @param provinces
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Provinces provinces, @PathVariable String id) {
        // 设置主键值
        provinces.setProvinceid(id);
        // 调用 ProvincesService 实现修改 Provinces
        provincesService.update(provinces);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Provinces 数据
     * @param provinces
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Provinces provinces) {
        // 调用 ProvincesService 实现添加 Provinces
        provincesService.add(provinces);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Provinces 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Provinces> findById(@PathVariable String id){
        // 调用 ProvincesService 实现根据主键查询 Provinces
        Provinces provinces = provincesService.findById(id);
        return new Result<Provinces>(true, StatusCode.OK, "ID 查询成功!", provinces);
    }

    /**
     * 查询 Provinces 全部数据
     * @return
     */
    @GetMapping
    public Result<List<Provinces>> findAll() {
        // 调用 ProvincesService 实现查询所有 Provinces
        List<Provinces> list = provincesService.findAll();
        return new Result<List<Provinces>>(true, StatusCode.OK, "查询成功!", list) ;
    }
}
