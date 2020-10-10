package com.changgou.order.controller;

import com.changgou.order.pojo.UndoLog;
import com.changgou.order.service.UndoLogService;
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
@RequestMapping("/undoLog")
@CrossOrigin
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    /**
     * UndoLog 分页条件搜索实现
     *
     * @param undoLog
     * @param page    当前页
     * @param size    每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) UndoLog undoLog, @PathVariable int page, @PathVariable int size) {
        // 调用 UndoLogService 实现分页条件查询 UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(undoLog, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * UndoLog 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 UndoLogService 实现分页查询 UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param undoLog
     * @return
     */
    @PostMapping("/search")
    public Result<List<UndoLog>> findList(@RequestBody(required = false) UndoLog undoLog) {
        //调用 UndoLogService 实现条件查询 UndoLog
        List<UndoLog> list = undoLogService.findList(undoLog);
        return new Result<List<UndoLog>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 UndoLogService 实现根据主键删除
        undoLogService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 UndoLog数据
     *
     * @param undoLog
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody UndoLog undoLog, @PathVariable Long id) {
        // 设置主键值
        undoLog.setId(id);
        // 调用 UndoLogService 实现修改 UndoLog
        undoLogService.update(undoLog);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 UndoLog 数据
     *
     * @param undoLog
     * @return
     */
    @PostMapping
    public Result add(@RequestBody UndoLog undoLog) {
        // 调用 UndoLogService 实现添加 UndoLog
        undoLogService.add(undoLog);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 UndoLog 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<UndoLog> findById(@PathVariable Long id) {
        // 调用 UndoLogService 实现根据主键查询 UndoLog
        UndoLog undoLog = undoLogService.findById(id);
        return new Result<UndoLog>(true, StatusCode.OK, "ID 查询成功!", undoLog);
    }

    /**
     * 查询 UndoLog 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<UndoLog>> findAll() {
        // 调用 UndoLogService 实现查询所有 UndoLog
        List<UndoLog> list = undoLogService.findAll();
        return new Result<List<UndoLog>>(true, StatusCode.OK, "查询成功!", list);
    }
}
