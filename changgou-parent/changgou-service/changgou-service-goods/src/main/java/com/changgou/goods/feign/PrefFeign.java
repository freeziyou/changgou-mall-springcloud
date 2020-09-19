package com.changgou.goods.feign;

import com.changgou.goods.pojo.Pref;
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
@RequestMapping("/pref")
public interface PrefFeign {

    /**
     * Pref 分页条件搜索实现
     *
     * @param pref
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Pref pref, @PathVariable int page, @PathVariable int size);

    /**
     * Pref 分页搜索实现
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
     * @param pref
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Pref>> findList(@RequestBody(required = false) Pref pref);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Pref数据
     *
     * @param pref
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Pref pref, @PathVariable Integer id);

    /**
     * 新增 Pref 数据
     *
     * @param pref
     * @return
     */
    @PostMapping
    Result add(@RequestBody Pref pref);

    /**
     * 根据 ID 查询 Pref 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Pref> findById(@PathVariable Integer id);

    /**
     * 查询 Pref 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Pref>> findAll();
}