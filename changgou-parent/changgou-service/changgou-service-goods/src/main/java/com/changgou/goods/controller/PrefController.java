package com.changgou.goods.controller;

import com.changgou.goods.pojo.Pref;
import com.changgou.goods.service.PrefService;
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
@RequestMapping("/pref")
@CrossOrigin
public class PrefController {

    @Autowired
    private PrefService prefService;

    /**
     * Pref 分页条件搜索实现
     *
     * @param pref
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Pref pref, @PathVariable int page, @PathVariable int size) {
        // 调用 PrefService 实现分页条件查询 Pref
        PageInfo<Pref> pageInfo = prefService.findPage(pref, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Pref 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 PrefService 实现分页查询 Pref
        PageInfo<Pref> pageInfo = prefService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param pref
     * @return
     */
    @PostMapping("/search")
    public Result<List<Pref>> findList(@RequestBody(required = false) Pref pref) {
        //调用 PrefService 实现条件查询 Pref
        List<Pref> list = prefService.findList(pref);
        return new Result<List<Pref>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 PrefService 实现根据主键删除
        prefService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Pref数据
     *
     * @param pref
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Pref pref, @PathVariable Integer id) {
        // 设置主键值
        pref.setId(id);
        // 调用 PrefService 实现修改 Pref
        prefService.update(pref);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Pref 数据
     *
     * @param pref
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Pref pref) {
        // 调用 PrefService 实现添加 Pref
        prefService.add(pref);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Pref 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Pref> findById(@PathVariable Integer id) {
        // 调用 PrefService 实现根据主键查询 Pref
        Pref pref = prefService.findById(id);
        return new Result<Pref>(true, StatusCode.OK, "ID 查询成功!", pref);
    }

    /**
     * 查询 Pref 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Pref>> findAll() {
        // 调用 PrefService 实现查询所有 Pref
        List<Pref> list = prefService.findAll();
        return new Result<List<Pref>>(true, StatusCode.OK, "查询成功!", list);
    }
}
