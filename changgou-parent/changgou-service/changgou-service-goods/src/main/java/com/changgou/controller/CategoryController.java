package com.changgou.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.service.CategoryService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/18/2020 14:12
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
     * @param category 查询条件
     * @param page     当前页
     * @param size     每页显示多少条
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Category category, @PathVariable int page, @PathVariable int size) {
        // 执行搜索
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Category 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 分页查询
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param category 查询条件
     * @return 分页结果
     */
    @PostMapping("/search")
    public Result<List<Category>> findList(@RequestBody(required = false) Category category) {
        List<Category> list = categoryService.findList(category);
        return new Result<List<Category>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id id
     * @return 结果消息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Category 数据
     *
     * @param category 目录
     * @param id       id
     * @return 结果消息
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Category category, @PathVariable Integer id) {
        category.setId(id);
        categoryService.update(category);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Category 数据
     *
     * @param category 目录
     * @return 结果消息
     */
    @PostMapping
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Category 数据
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        return new Result<Category>(true, StatusCode.OK, "id 查询成功!", category);
    }

    /**
     * 查询 Category 全部数据
     *
     * @return 查询结果
     */
    @GetMapping
    public Result<List<Category>> findAll() {
        List<Category> list = categoryService.findAll();
        return new Result<List<Category>>(true, StatusCode.OK, "查询成功!", list);
    }

    /**
     * 根据父 ID 查询
     */
    @RequestMapping("/list/{pid}")
    public Result<Category> findByPrantId(@PathVariable(value = "pid") Integer pid) {
        // 根据父节点 ID 查询
        List<Category> list = categoryService.findByParentId(pid);
        return new Result<Category>(true, StatusCode.OK, "查询成功!", list);
    }
}
