package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnOrderItem;
import com.changgou.order.service.ReturnOrderItemService;
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
@RequestMapping("/returnOrderItem")
@CrossOrigin
public class ReturnOrderItemController {

    @Autowired
    private ReturnOrderItemService returnOrderItemService;

    /**
     * ReturnOrderItem 分页条件搜索实现
     *
     * @param returnOrderItem
     * @param page            当前页
     * @param size            每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) ReturnOrderItem returnOrderItem, @PathVariable int page, @PathVariable int size) {
        // 调用 ReturnOrderItemService 实现分页条件查询 ReturnOrderItem
        PageInfo<ReturnOrderItem> pageInfo = returnOrderItemService.findPage(returnOrderItem, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * ReturnOrderItem 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ReturnOrderItemService 实现分页查询 ReturnOrderItem
        PageInfo<ReturnOrderItem> pageInfo = returnOrderItemService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param returnOrderItem
     * @return
     */
    @PostMapping("/search")
    public Result<List<ReturnOrderItem>> findList(@RequestBody(required = false) ReturnOrderItem returnOrderItem) {
        //调用 ReturnOrderItemService 实现条件查询 ReturnOrderItem
        List<ReturnOrderItem> list = returnOrderItemService.findList(returnOrderItem);
        return new Result<List<ReturnOrderItem>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 ReturnOrderItemService 实现根据主键删除
        returnOrderItemService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 ReturnOrderItem数据
     *
     * @param returnOrderItem
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody ReturnOrderItem returnOrderItem, @PathVariable Long id) {
        // 设置主键值
        returnOrderItem.setId(id);
        // 调用 ReturnOrderItemService 实现修改 ReturnOrderItem
        returnOrderItemService.update(returnOrderItem);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 ReturnOrderItem 数据
     *
     * @param returnOrderItem
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ReturnOrderItem returnOrderItem) {
        // 调用 ReturnOrderItemService 实现添加 ReturnOrderItem
        returnOrderItemService.add(returnOrderItem);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 ReturnOrderItem 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnOrderItem> findById(@PathVariable Long id) {
        // 调用 ReturnOrderItemService 实现根据主键查询 ReturnOrderItem
        ReturnOrderItem returnOrderItem = returnOrderItemService.findById(id);
        return new Result<ReturnOrderItem>(true, StatusCode.OK, "ID 查询成功!", returnOrderItem);
    }

    /**
     * 查询 ReturnOrderItem 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<ReturnOrderItem>> findAll() {
        // 调用 ReturnOrderItemService 实现查询所有 ReturnOrderItem
        List<ReturnOrderItem> list = returnOrderItemService.findAll();
        return new Result<List<ReturnOrderItem>>(true, StatusCode.OK, "查询成功!", list);
    }
}
