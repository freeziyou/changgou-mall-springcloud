package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
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
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据分类实现品牌列表查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable("id") Integer categoryId) {
        List<Brand> brandList = brandService.findByCategory(categoryId);
        return new Result<List<Brand>>(true, StatusCode.OK, "查询成功！", brandList);
    }

    /**
     * Brand 分页条件搜索实现
     *
     * @param brand 查询条件
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Brand brand, @PathVariable int page, @PathVariable int size) {
        // 调用 BrandService 实现分页条件查询 Brand
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Brand 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 BrandService 实现分页查询 Brand
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param brand
     * @return
     */
    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand) {
        //调用 BrandService 实现条件查询 Brand
        List<Brand> list = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 BrandService 实现根据主键删除
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Brand数据
     *
     * @param brand
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id) {
        // 设置主键值
        brand.setId(id);
        // 调用 BrandService 实现修改 Brand
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Brand 数据
     *
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        // 调用 BrandService 实现添加 Brand
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Brand 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable Integer id) {
        // 调用 BrandService 实现根据主键查询 Brand
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true, StatusCode.OK, "ID 查询成功!", brand);
    }

    /**
     * 查询 Brand 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Brand>> findAll() {
        // 调用 BrandService 实现查询所有 Brand
        List<Brand> list = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK, "查询成功!", list);
    }
}
