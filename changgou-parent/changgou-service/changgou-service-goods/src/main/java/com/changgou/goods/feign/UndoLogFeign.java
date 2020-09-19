package com.changgou.goods.feign;

import com.changgou.goods.pojo.UndoLog;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@FeignClient(name = "goods")
@RequestMapping("/undoLog")
public interface UndoLogFeign {

    /**
     * UndoLog 分页条件搜索实现
     *
     * @param undoLog
     * @param page    当前页
     * @param size    每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) UndoLog undoLog, @PathVariable int page, @PathVariable int size);

    /**
     * UndoLog 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /**
     * 多条件搜索品牌数据
     *
     * @param undoLog
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<UndoLog>> findList(@RequestBody(required = false) UndoLog undoLog);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Long id);

    /**
     * 修改 UndoLog数据
     *
     * @param undoLog
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody UndoLog undoLog, @PathVariable Long id);

    /**
     * 新增 UndoLog 数据
     *
     * @param undoLog
     * @return
     */
    @PostMapping
    Result add(@RequestBody UndoLog undoLog);

    /**
     * 根据 ID 查询 UndoLog 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<UndoLog> findById(@PathVariable Long id);

    /**
     * 查询 UndoLog 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<UndoLog>> findAll();
}