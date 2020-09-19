package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
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
@RequestMapping("/spu")
public interface SpuFeign {

    /**
     * Spu 分页条件搜索实现
     *
     * @param spu
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Spu spu, @PathVariable int page, @PathVariable int size);

    /**
     * Spu 分页搜索实现
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
     * @param spu
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Spu>> findList(@RequestBody(required = false) Spu spu);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Long id);

    /**
     * 修改 Spu数据
     *
     * @param spu
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Spu spu, @PathVariable Long id);

    /**
     * 新增 Spu 数据
     *
     * @param spu
     * @return
     */
    @PostMapping
    Result add(@RequestBody Spu spu);

    /**
     * 根据 ID 查询 Spu 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Spu> findById(@PathVariable Long id);

    /**
     * 查询 Spu 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Spu>> findAll();
}