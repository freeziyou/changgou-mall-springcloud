package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spec;
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
@RequestMapping("/spec")
public interface SpecFeign {

    /**
     * Spec 分页条件搜索实现
     *
     * @param spec
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Spec spec, @PathVariable int page, @PathVariable int size);

    /**
     * Spec 分页搜索实现
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
     * @param spec
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Spec>> findList(@RequestBody(required = false) Spec spec);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Spec数据
     *
     * @param spec
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Spec spec, @PathVariable Integer id);

    /**
     * 新增 Spec 数据
     *
     * @param spec
     * @return
     */
    @PostMapping
    Result add(@RequestBody Spec spec);

    /**
     * 根据 ID 查询 Spec 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Spec> findById(@PathVariable Integer id);

    /**
     * 查询 Spec 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Spec>> findAll();
}