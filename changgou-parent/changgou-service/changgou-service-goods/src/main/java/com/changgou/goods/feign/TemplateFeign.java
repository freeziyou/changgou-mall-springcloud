package com.changgou.goods.feign;

import com.changgou.goods.pojo.Template;
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
@RequestMapping("/template")
public interface TemplateFeign {

    /**
     * Template 分页条件搜索实现
     *
     * @param template
     * @param page     当前页
     * @param size     每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Template template, @PathVariable int page, @PathVariable int size);

    /**
     * Template 分页搜索实现
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
     * @param template
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Template>> findList(@RequestBody(required = false) Template template);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Template数据
     *
     * @param template
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Template template, @PathVariable Integer id);

    /**
     * 新增 Template 数据
     *
     * @param template
     * @return
     */
    @PostMapping
    Result add(@RequestBody Template template);

    /**
     * 根据 ID 查询 Template 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Template> findById(@PathVariable Integer id);

    /**
     * 查询 Template 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Template>> findAll();
}