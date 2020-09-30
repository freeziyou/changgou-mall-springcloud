package com.changgou.user.controller;

import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
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
@RequestMapping("/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    /**
     * Cities 分页条件搜索实现
     * @param cities
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Cities cities, @PathVariable int page, @PathVariable int size) {
        // 调用 CitiesService 实现分页条件查询 Cities
        PageInfo<Cities> pageInfo = citiesService.findPage(cities, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Cities 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 CitiesService 实现分页查询 Cities
        PageInfo<Cities> pageInfo = citiesService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     * @param cities
     * @return
     */
    @PostMapping("/search")
    public Result<List<Cities>> findList(@RequestBody(required = false) Cities cities) {
        //调用 CitiesService 实现条件查询 Cities
        List<Cities> list = citiesService.findList(cities);
        return new Result<List<Cities>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 CitiesService 实现根据主键删除
        citiesService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Cities数据
     * @param cities
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Cities cities, @PathVariable String id) {
        // 设置主键值
        cities.setCityid(id);
        // 调用 CitiesService 实现修改 Cities
        citiesService.update(cities);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Cities 数据
     * @param cities
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Cities cities) {
        // 调用 CitiesService 实现添加 Cities
        citiesService.add(cities);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Cities 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Cities> findById(@PathVariable String id){
        // 调用 CitiesService 实现根据主键查询 Cities
        Cities cities = citiesService.findById(id);
        return new Result<Cities>(true, StatusCode.OK, "ID 查询成功!", cities);
    }

    /**
     * 查询 Cities 全部数据
     * @return
     */
    @GetMapping
    public Result<List<Cities>> findAll() {
        // 调用 CitiesService 实现查询所有 Cities
        List<Cities> list = citiesService.findAll();
        return new Result<List<Cities>>(true, StatusCode.OK, "查询成功!", list) ;
    }
}
