package com.changgou.order.controller;

import com.changgou.order.pojo.OrderLog;
import com.changgou.order.service.OrderLogService;
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
@RequestMapping("/orderLog")
@CrossOrigin
public class OrderLogController {

    @Autowired
    private OrderLogService orderLogService;

    /**
     * OrderLog 分页条件搜索实现
     *
     * @param orderLog
     * @param page     当前页
     * @param size     每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) OrderLog orderLog, @PathVariable int page, @PathVariable int size) {
        // 调用 OrderLogService 实现分页条件查询 OrderLog
        PageInfo<OrderLog> pageInfo = orderLogService.findPage(orderLog, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * OrderLog 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 OrderLogService 实现分页查询 OrderLog
        PageInfo<OrderLog> pageInfo = orderLogService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param orderLog
     * @return
     */
    @PostMapping("/search")
    public Result<List<OrderLog>> findList(@RequestBody(required = false) OrderLog orderLog) {
        //调用 OrderLogService 实现条件查询 OrderLog
        List<OrderLog> list = orderLogService.findList(orderLog);
        return new Result<List<OrderLog>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 OrderLogService 实现根据主键删除
        orderLogService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 OrderLog数据
     *
     * @param orderLog
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody OrderLog orderLog, @PathVariable String id) {
        // 设置主键值
        orderLog.setId(id);
        // 调用 OrderLogService 实现修改 OrderLog
        orderLogService.update(orderLog);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 OrderLog 数据
     *
     * @param orderLog
     * @return
     */
    @PostMapping
    public Result add(@RequestBody OrderLog orderLog) {
        // 调用 OrderLogService 实现添加 OrderLog
        orderLogService.add(orderLog);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 OrderLog 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OrderLog> findById(@PathVariable String id) {
        // 调用 OrderLogService 实现根据主键查询 OrderLog
        OrderLog orderLog = orderLogService.findById(id);
        return new Result<OrderLog>(true, StatusCode.OK, "ID 查询成功!", orderLog);
    }

    /**
     * 查询 OrderLog 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<OrderLog>> findAll() {
        // 调用 OrderLogService 实现查询所有 OrderLog
        List<OrderLog> list = orderLogService.findAll();
        return new Result<List<OrderLog>>(true, StatusCode.OK, "查询成功!", list);
    }
}
