package com.changgou.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/18/2020 13:40
 * @description TODO
 */
@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    /**
     * Template 分页条件搜索实现
     *
     * @param template 模板
     * @param page     当前页
     * @param size     每页显示多少条
     * @return 结果消息
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Template template, @PathVariable int page, @PathVariable int size) {
        PageInfo<Template> pageInfo = templateService.findPage(template, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "页面条件查询成功!", pageInfo);
    }

    /**
     * Template 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 结果消息
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param template 模板
     * @return 结果消息
     */
    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody(required = false) Template template) {
        List<Template> list = templateService.findList(template);
        return new Result<List<Template>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /***
     * 根据 ID 删除品牌数据
     * @param id id
     * @return 结果消息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Template 数据
     *
     * @param template 模板
     * @param id       id
     * @return 结果消息
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Template template, @PathVariable Integer id) {
        template.setId(id);
        templateService.update(template);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增Template数据
     *
     * @param template 模板
     * @return 结果消息
     */
    @PostMapping
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Template 数据
     *
     * @param id id
     * @return 结果消息
     */
    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id) {
        Template template = templateService.findById(id);
        return new Result<Template>(true, StatusCode.OK, "id 查询成功!", template);
    }

    /**
     * 查询 Template 全部数据
     *
     * @return 结果消息
     */
    @GetMapping
    public Result<List<Template>> findAll() {
        List<Template> list = templateService.findAll();
        return new Result<List<Template>>(true, StatusCode.OK, "查询成功!", list);
    }
}
