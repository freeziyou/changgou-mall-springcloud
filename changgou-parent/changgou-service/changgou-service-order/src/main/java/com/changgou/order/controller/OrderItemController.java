package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.OrderItemService;
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
@RequestMapping("/orderItem")
@CrossOrigin
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /**
     * OrderItem 分页条件搜索实现
     *
     * @param orderItem
     * @param page      当前页
     * @param size      每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) OrderItem orderItem, @PathVariable int page, @PathVariable int size) {
        // 调用 OrderItemService 实现分页条件查询 OrderItem
        PageInfo<OrderItem> pageInfo = orderItemService.findPage(orderItem, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * OrderItem 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 OrderItemService 实现分页查询 OrderItem
        PageInfo<OrderItem> pageInfo = orderItemService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param orderItem
     * @return
     */
    @PostMapping("/search")
    public Result<List<OrderItem>> findList(@RequestBody(required = false) OrderItem orderItem) {
        //调用 OrderItemService 实现条件查询 OrderItem
        List<OrderItem> list = orderItemService.findList(orderItem);
        return new Result<List<OrderItem>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 OrderItemService 实现根据主键删除
        orderItemService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 OrderItem数据
     *
     * @param orderItem
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody OrderItem orderItem, @PathVariable String id) {
        // 设置主键值
        orderItem.setId(id);
        // 调用 OrderItemService 实现修改 OrderItem
        orderItemService.update(orderItem);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 OrderItem 数据
     *
     * @param orderItem
     * @return
     */
    @PostMapping
    public Result add(@RequestBody OrderItem orderItem) {
        // 调用 OrderItemService 实现添加 OrderItem
        orderItemService.add(orderItem);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 OrderItem 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OrderItem> findById(@PathVariable String id) {
        // 调用 OrderItemService 实现根据主键查询 OrderItem
        OrderItem orderItem = orderItemService.findById(id);
        return new Result<OrderItem>(true, StatusCode.OK, "ID 查询成功!", orderItem);
    }

    /**
     * 查询 OrderItem 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<OrderItem>> findAll() {
        // 调用 OrderItemService 实现查询所有 OrderItem
        List<OrderItem> list = orderItemService.findAll();
        return new Result<List<OrderItem>>(true, StatusCode.OK, "查询成功!", list);
    }
}
