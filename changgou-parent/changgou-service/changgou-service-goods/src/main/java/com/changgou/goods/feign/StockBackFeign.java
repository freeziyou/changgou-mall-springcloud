package com.changgou.goods.feign;

import com.changgou.goods.pojo.StockBack;
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
@RequestMapping("/stockBack")
public interface StockBackFeign {

    /**
     * StockBack 分页条件搜索实现
     *
     * @param stockBack
     * @param page      当前页
     * @param size      每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) StockBack stockBack, @PathVariable int page, @PathVariable int size);

    /**
     * StockBack 分页搜索实现
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
     * @param stockBack
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<StockBack>> findList(@RequestBody(required = false) StockBack stockBack);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable String id);

    /**
     * 修改 StockBack数据
     *
     * @param stockBack
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody StockBack stockBack, @PathVariable String id);

    /**
     * 新增 StockBack 数据
     *
     * @param stockBack
     * @return
     */
    @PostMapping
    Result add(@RequestBody StockBack stockBack);

    /**
     * 根据 ID 查询 StockBack 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<StockBack> findById(@PathVariable String id);

    /**
     * 查询 StockBack 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<StockBack>> findAll();
}