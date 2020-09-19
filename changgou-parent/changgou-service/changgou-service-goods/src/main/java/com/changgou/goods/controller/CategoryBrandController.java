package com.changgou.goods.controller;

import com.changgou.goods.pojo.CategoryBrand;
import com.changgou.goods.service.CategoryBrandService;
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
@RequestMapping("/categoryBrand")
@CrossOrigin
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    /**
     * CategoryBrand 分页条件搜索实现
     *
     * @param categoryBrand
     * @param page          当前页
     * @param size          每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) CategoryBrand categoryBrand, @PathVariable int page, @PathVariable int size) {
        // 调用 CategoryBrandService 实现分页条件查询 CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(categoryBrand, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * CategoryBrand 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 CategoryBrandService 实现分页查询 CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param categoryBrand
     * @return
     */
    @PostMapping("/search")
    public Result<List<CategoryBrand>> findList(@RequestBody(required = false) CategoryBrand categoryBrand) {
        //调用 CategoryBrandService 实现条件查询 CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findList(categoryBrand);
        return new Result<List<CategoryBrand>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 CategoryBrandService 实现根据主键删除
        categoryBrandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 CategoryBrand数据
     *
     * @param categoryBrand
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody CategoryBrand categoryBrand, @PathVariable Integer id) {
        // 设置主键值
        categoryBrand.setCategoryId(id);
        // 调用 CategoryBrandService 实现修改 CategoryBrand
        categoryBrandService.update(categoryBrand);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 CategoryBrand 数据
     *
     * @param categoryBrand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody CategoryBrand categoryBrand) {
        // 调用 CategoryBrandService 实现添加 CategoryBrand
        categoryBrandService.add(categoryBrand);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 CategoryBrand 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<CategoryBrand> findById(@PathVariable Integer id) {
        // 调用 CategoryBrandService 实现根据主键查询 CategoryBrand
        CategoryBrand categoryBrand = categoryBrandService.findById(id);
        return new Result<CategoryBrand>(true, StatusCode.OK, "ID 查询成功!", categoryBrand);
    }

    /**
     * 查询 CategoryBrand 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<CategoryBrand>> findAll() {
        // 调用 CategoryBrandService 实现查询所有 CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findAll();
        return new Result<List<CategoryBrand>>(true, StatusCode.OK, "查询成功!", list);
    }
}
