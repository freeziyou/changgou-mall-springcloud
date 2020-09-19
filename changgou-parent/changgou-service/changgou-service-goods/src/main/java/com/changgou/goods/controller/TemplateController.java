package com.changgou.goods.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
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
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 根据分类查询模板数据
     *
     * @param id 分类 ID
     */
    @GetMapping("/category/{id}")
    public Result<Template> findByCategoryId(@PathVariable("id") Integer id) {
        // 调用 Service 查询
        Template template = templateService.findByCategoryId(id);
        return new Result<Template>(true, StatusCode.OK, "查询成功", template);
    }

    /**
     * Template 分页条件搜索实现
     *
     * @param template
     * @param page     当前页
     * @param size     每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Template template, @PathVariable int page, @PathVariable int size) {
        // 调用 TemplateService 实现分页条件查询 Template
        PageInfo<Template> pageInfo = templateService.findPage(template, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Template 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 TemplateService 实现分页查询 Template
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param template
     * @return
     */
    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody(required = false) Template template) {
        //调用 TemplateService 实现条件查询 Template
        List<Template> list = templateService.findList(template);
        return new Result<List<Template>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 TemplateService 实现根据主键删除
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Template数据
     *
     * @param template
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Template template, @PathVariable Integer id) {
        // 设置主键值
        template.setId(id);
        // 调用 TemplateService 实现修改 Template
        templateService.update(template);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Template 数据
     *
     * @param template
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Template template) {
        // 调用 TemplateService 实现添加 Template
        templateService.add(template);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Template 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id) {
        // 调用 TemplateService 实现根据主键查询 Template
        Template template = templateService.findById(id);
        return new Result<Template>(true, StatusCode.OK, "ID 查询成功!", template);
    }

    /**
     * 查询 Template 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Template>> findAll() {
        // 调用 TemplateService 实现查询所有 Template
        List<Template> list = templateService.findAll();
        return new Result<List<Template>>(true, StatusCode.OK, "查询成功!", list);
    }
}
