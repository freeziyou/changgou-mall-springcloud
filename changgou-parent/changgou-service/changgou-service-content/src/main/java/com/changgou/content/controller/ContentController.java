package com.changgou.content.controller;

import com.changgou.content.pojo.Content;
import com.changgou.content.service.ContentService;
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
@RequestMapping("/content")
@CrossOrigin
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据 categoryId 查询广告集合
     *
     * @param id
     * @return
     */
    @GetMapping("/list/category/{id}")
    public Result<List<Content>> findByCategory(@PathVariable Long id) {
        // 根据分类 ID 查询广告集合
        List<Content> contents = contentService.findByCategory(id);
        return new Result<List<Content>>(true, StatusCode.OK, "根据 categoryId 查询广告集合成功!", contents);
    }

    /**
     * Content 分页条件搜索实现
     *
     * @param content
     * @param page    当前页
     * @param size    每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Content content, @PathVariable int page, @PathVariable int size) {
        // 调用 ContentService 实现分页条件查询 Content
        PageInfo<Content> pageInfo = contentService.findPage(content, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Content 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ContentService 实现分页查询 Content
        PageInfo<Content> pageInfo = contentService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param content
     * @return
     */
    @PostMapping("/search")
    public Result<List<Content>> findList(@RequestBody(required = false) Content content) {
        //调用 ContentService 实现条件查询 Content
        List<Content> list = contentService.findList(content);
        return new Result<List<Content>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 ContentService 实现根据主键删除
        contentService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Content数据
     *
     * @param content
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Content content, @PathVariable Long id) {
        // 设置主键值
        content.setId(id);
        // 调用 ContentService 实现修改 Content
        contentService.update(content);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Content 数据
     *
     * @param content
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Content content) {
        // 调用 ContentService 实现添加 Content
        contentService.add(content);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Content 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Content> findById(@PathVariable Long id) {
        // 调用 ContentService 实现根据主键查询 Content
        Content content = contentService.findById(id);
        return new Result<Content>(true, StatusCode.OK, "ID 查询成功!", content);
    }

    /**
     * 查询 Content 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Content>> findAll() {
        // 调用 ContentService 实现查询所有 Content
        List<Content> list = contentService.findAll();
        return new Result<List<Content>>(true, StatusCode.OK, "查询成功!", list);
    }
}
