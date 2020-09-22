package com.changgou.content.controller;

import com.changgou.content.pojo.ContentCategory;
import com.changgou.content.service.ContentCategoryService;
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
@RequestMapping("/contentCategory")
@CrossOrigin
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * ContentCategory 分页条件搜索实现
     * @param contentCategory
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) ContentCategory contentCategory, @PathVariable int page, @PathVariable int size) {
        // 调用 ContentCategoryService 实现分页条件查询 ContentCategory
        PageInfo<ContentCategory> pageInfo = contentCategoryService.findPage(contentCategory, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * ContentCategory 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 ContentCategoryService 实现分页查询 ContentCategory
        PageInfo<ContentCategory> pageInfo = contentCategoryService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     * @param contentCategory
     * @return
     */
    @PostMapping("/search")
    public Result<List<ContentCategory>> findList(@RequestBody(required = false) ContentCategory contentCategory) {
        //调用 ContentCategoryService 实现条件查询 ContentCategory
        List<ContentCategory> list = contentCategoryService.findList(contentCategory);
        return new Result<List<ContentCategory>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 ContentCategoryService 实现根据主键删除
        contentCategoryService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 ContentCategory数据
     * @param contentCategory
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody ContentCategory contentCategory, @PathVariable Long id) {
        // 设置主键值
        contentCategory.setId(id);
        // 调用 ContentCategoryService 实现修改 ContentCategory
        contentCategoryService.update(contentCategory);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 ContentCategory 数据
     * @param contentCategory
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ContentCategory contentCategory) {
        // 调用 ContentCategoryService 实现添加 ContentCategory
        contentCategoryService.add(contentCategory);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 ContentCategory 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ContentCategory> findById(@PathVariable Long id){
        // 调用 ContentCategoryService 实现根据主键查询 ContentCategory
        ContentCategory contentCategory = contentCategoryService.findById(id);
        return new Result<ContentCategory>(true, StatusCode.OK, "ID 查询成功!", contentCategory);
    }

    /**
     * 查询 ContentCategory 全部数据
     * @return
     */
    @GetMapping
    public Result<List<ContentCategory>> findAll() {
        // 调用 ContentCategoryService 实现查询所有 ContentCategory
        List<ContentCategory> list = contentCategoryService.findAll();
        return new Result<List<ContentCategory>>(true, StatusCode.OK, "查询成功!", list) ;
    }
}
