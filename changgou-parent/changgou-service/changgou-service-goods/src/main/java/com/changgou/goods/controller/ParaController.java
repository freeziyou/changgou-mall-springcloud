package com.changgou.goods.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
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
@RequestMapping("/para")
@CrossOrigin
public class ParaController {

    @Autowired
    private ParaService paraService;

    /**
     * 根据分类 ID 查询参数列表
     *
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Para>> getByCategoryId(@PathVariable("id") Integer id) {
        // 根据分类 ID 查询对应的参数信息
        List<Para> paraList = paraService.findByCategoryId(id);
        return new Result<List<Para>>(true, StatusCode.OK, "查询分类对应的品牌成功！", paraList);
    }

    /**
     * Para 分页条件搜索实现
     *
     * @param para
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Para para, @PathVariable int page, @PathVariable int size) {
        // 调用 ParaService 实现分页条件查询 Para
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Para 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ParaService 实现分页查询 Para
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param para
     * @return
     */
    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody(required = false) Para para) {
        //调用 ParaService 实现条件查询 Para
        List<Para> list = paraService.findList(para);
        return new Result<List<Para>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 ParaService 实现根据主键删除
        paraService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Para数据
     *
     * @param para
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Para para, @PathVariable Integer id) {
        // 设置主键值
        para.setId(id);
        // 调用 ParaService 实现修改 Para
        paraService.update(para);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Para 数据
     *
     * @param para
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Para para) {
        // 调用 ParaService 实现添加 Para
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Para 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable Integer id) {
        // 调用 ParaService 实现根据主键查询 Para
        Para para = paraService.findById(id);
        return new Result<Para>(true, StatusCode.OK, "ID 查询成功!", para);
    }

    /**
     * 查询 Para 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Para>> findAll() {
        // 调用 ParaService 实现查询所有 Para
        List<Para> list = paraService.findAll();
        return new Result<List<Para>>(true, StatusCode.OK, "查询成功!", list);
    }
}
