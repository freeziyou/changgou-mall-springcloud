package com.changgou.order.controller;

import com.changgou.order.pojo.Order;
import com.changgou.order.service.OrderService;
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
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Order 分页条件搜索实现
     *
     * @param order
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Order order, @PathVariable int page, @PathVariable int size) {
        // 调用 OrderService 实现分页条件查询 Order
        PageInfo<Order> pageInfo = orderService.findPage(order, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Order 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 OrderService 实现分页查询 Order
        PageInfo<Order> pageInfo = orderService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param order
     * @return
     */
    @PostMapping("/search")
    public Result<List<Order>> findList(@RequestBody(required = false) Order order) {
        //调用 OrderService 实现条件查询 Order
        List<Order> list = orderService.findList(order);
        return new Result<List<Order>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 OrderService 实现根据主键删除
        orderService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Order数据
     *
     * @param order
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Order order, @PathVariable String id) {
        // 设置主键值
        order.setId(id);
        // 调用 OrderService 实现修改 Order
        orderService.update(order);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Order 数据
     *
     * @param order
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Order order) {
        // 调用 OrderService 实现添加 Order
        orderService.add(order);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Order 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Order> findById(@PathVariable String id) {
        // 调用 OrderService 实现根据主键查询 Order
        Order order = orderService.findById(id);
        return new Result<Order>(true, StatusCode.OK, "ID 查询成功!", order);
    }

    /**
     * 查询 Order 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Order>> findAll() {
        // 调用 OrderService 实现查询所有 Order
        List<Order> list = orderService.findAll();
        return new Result<List<Order>>(true, StatusCode.OK, "查询成功!", list);
    }
}
