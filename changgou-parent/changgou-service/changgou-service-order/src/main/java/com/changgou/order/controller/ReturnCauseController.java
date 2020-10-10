package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnCause;
import com.changgou.order.service.ReturnCauseService;
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
@RequestMapping("/returnCause")
@CrossOrigin
public class ReturnCauseController {

    @Autowired
    private ReturnCauseService returnCauseService;

    /**
     * ReturnCause 分页条件搜索实现
     *
     * @param returnCause
     * @param page        当前页
     * @param size        每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) ReturnCause returnCause, @PathVariable int page, @PathVariable int size) {
        // 调用 ReturnCauseService 实现分页条件查询 ReturnCause
        PageInfo<ReturnCause> pageInfo = returnCauseService.findPage(returnCause, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * ReturnCause 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ReturnCauseService 实现分页查询 ReturnCause
        PageInfo<ReturnCause> pageInfo = returnCauseService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param returnCause
     * @return
     */
    @PostMapping("/search")
    public Result<List<ReturnCause>> findList(@RequestBody(required = false) ReturnCause returnCause) {
        //调用 ReturnCauseService 实现条件查询 ReturnCause
        List<ReturnCause> list = returnCauseService.findList(returnCause);
        return new Result<List<ReturnCause>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 ReturnCauseService 实现根据主键删除
        returnCauseService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 ReturnCause数据
     *
     * @param returnCause
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody ReturnCause returnCause, @PathVariable Integer id) {
        // 设置主键值
        returnCause.setId(id);
        // 调用 ReturnCauseService 实现修改 ReturnCause
        returnCauseService.update(returnCause);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 ReturnCause 数据
     *
     * @param returnCause
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ReturnCause returnCause) {
        // 调用 ReturnCauseService 实现添加 ReturnCause
        returnCauseService.add(returnCause);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 ReturnCause 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnCause> findById(@PathVariable Integer id) {
        // 调用 ReturnCauseService 实现根据主键查询 ReturnCause
        ReturnCause returnCause = returnCauseService.findById(id);
        return new Result<ReturnCause>(true, StatusCode.OK, "ID 查询成功!", returnCause);
    }

    /**
     * 查询 ReturnCause 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<ReturnCause>> findAll() {
        // 调用 ReturnCauseService 实现查询所有 ReturnCause
        List<ReturnCause> list = returnCauseService.findAll();
        return new Result<List<ReturnCause>>(true, StatusCode.OK, "查询成功!", list);
    }
}
