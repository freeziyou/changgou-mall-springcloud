package com.changgou.goods.feign;

import com.changgou.goods.pojo.Para;
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
@RequestMapping("/para")
public interface ParaFeign {

    /**
     * Para 分页条件搜索实现
     *
     * @param para
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Para para, @PathVariable int page, @PathVariable int size);

    /**
     * Para 分页搜索实现
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
     * @param para
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Para>> findList(@RequestBody(required = false) Para para);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Para数据
     *
     * @param para
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Para para, @PathVariable Integer id);

    /**
     * 新增 Para 数据
     *
     * @param para
     * @return
     */
    @PostMapping
    Result add(@RequestBody Para para);

    /**
     * 根据 ID 查询 Para 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Para> findById(@PathVariable Integer id);

    /**
     * 查询 Para 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Para>> findAll();
}