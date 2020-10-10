package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnOrder;
import com.changgou.order.service.ReturnOrderService;
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
@RequestMapping("/returnOrder")
@CrossOrigin
public class ReturnOrderController {

    @Autowired
    private ReturnOrderService returnOrderService;

    /**
     * ReturnOrder 分页条件搜索实现
     *
     * @param returnOrder
     * @param page        当前页
     * @param size        每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) ReturnOrder returnOrder, @PathVariable int page, @PathVariable int size) {
        // 调用 ReturnOrderService 实现分页条件查询 ReturnOrder
        PageInfo<ReturnOrder> pageInfo = returnOrderService.findPage(returnOrder, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * ReturnOrder 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ReturnOrderService 实现分页查询 ReturnOrder
        PageInfo<ReturnOrder> pageInfo = returnOrderService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param returnOrder
     * @return
     */
    @PostMapping("/search")
    public Result<List<ReturnOrder>> findList(@RequestBody(required = false) ReturnOrder returnOrder) {
        //调用 ReturnOrderService 实现条件查询 ReturnOrder
        List<ReturnOrder> list = returnOrderService.findList(returnOrder);
        return new Result<List<ReturnOrder>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 ReturnOrderService 实现根据主键删除
        returnOrderService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 ReturnOrder数据
     *
     * @param returnOrder
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody ReturnOrder returnOrder, @PathVariable Long id) {
        // 设置主键值
        returnOrder.setId(id);
        // 调用 ReturnOrderService 实现修改 ReturnOrder
        returnOrderService.update(returnOrder);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 ReturnOrder 数据
     *
     * @param returnOrder
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ReturnOrder returnOrder) {
        // 调用 ReturnOrderService 实现添加 ReturnOrder
        returnOrderService.add(returnOrder);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 ReturnOrder 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnOrder> findById(@PathVariable Long id) {
        // 调用 ReturnOrderService 实现根据主键查询 ReturnOrder
        ReturnOrder returnOrder = returnOrderService.findById(id);
        return new Result<ReturnOrder>(true, StatusCode.OK, "ID 查询成功!", returnOrder);
    }

    /**
     * 查询 ReturnOrder 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<ReturnOrder>> findAll() {
        // 调用 ReturnOrderService 实现查询所有 ReturnOrder
        List<ReturnOrder> list = returnOrderService.findAll();
        return new Result<List<ReturnOrder>>(true, StatusCode.OK, "查询成功!", list);
    }
}
