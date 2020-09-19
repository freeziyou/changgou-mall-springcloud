package com.changgou.goods.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
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
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Category 分页条件搜索实现
     *
     * @param category
     * @param page     当前页
     * @param size     每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Category category, @PathVariable int page, @PathVariable int size) {
        // 调用 CategoryService 实现分页条件查询 Category
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Category 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 CategoryService 实现分页查询 Category
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param category
     * @return
     */
    @PostMapping("/search")
    public Result<List<Category>> findList(@RequestBody(required = false) Category category) {
        //调用 CategoryService 实现条件查询 Category
        List<Category> list = categoryService.findList(category);
        return new Result<List<Category>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 CategoryService 实现根据主键删除
        categoryService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Category数据
     *
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Category category, @PathVariable Integer id) {
        // 设置主键值
        category.setId(id);
        // 调用 CategoryService 实现修改 Category
        categoryService.update(category);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Category 数据
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Category category) {
        // 调用 CategoryService 实现添加 Category
        categoryService.add(category);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Category 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Integer id) {
        // 调用 CategoryService 实现根据主键查询 Category
        Category category = categoryService.findById(id);
        return new Result<Category>(true, StatusCode.OK, "ID 查询成功!", category);
    }

    /**
     * 查询 Category 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Category>> findAll() {
        // 调用 CategoryService 实现查询所有 Category
        List<Category> list = categoryService.findAll();
        return new Result<List<Category>>(true, StatusCode.OK, "查询成功!", list);
    }

    /**
     * 根据父 ID 查询
     */
    @RequestMapping("/list/{pid}")
    public Result<List<Category>> findByPrantId(@PathVariable(value = "pid") Integer pid) {
        // 根据父节点 ID 查询
        List<Category> list = categoryService.findByParentId(pid);
        return new Result<List<Category>>(true, StatusCode.OK, "查询子节点成功!", list);
    }
}
