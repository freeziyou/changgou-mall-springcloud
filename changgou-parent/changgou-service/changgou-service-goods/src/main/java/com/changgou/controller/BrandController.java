package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/16/2020 12:51
 * @description TODO
 */
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页条件查询
     *
     * @param brand 搜索条件
     * @param page  当前页
     * @param size  每页显示多少条
     * @return 结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Brand brand, @PathVariable int page, @PathVariable int size) {
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * 分页查询
     *
     * @param page:当前页
     * @param size:每页显示多少条
     * @return 结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        int a = 10/0;
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 条件查询
     *
     * @param brand 品牌
     * @return 符合条件品牌
     */
    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand) {
        List<Brand> brands = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK, "条件查询成功!", brands);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id id
     * @return 结果消息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 根据 ID 修改品牌数据
     *
     * @param brand 品牌
     * @param id    id
     * @return 结果消息
     */
    @PostMapping("/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable(value = "id") Integer id) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加Brand成功!");
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Integer id) {
        Brand brand = brandService.findById(id);

        // 响应结果封装
        return new Result<Brand>(true, StatusCode.OK, "根据ID查询Brand成功!", brand);
    }

    /**
     * 查询所有品牌
     */
    @GetMapping()
    public Result<List<Brand>> findAll() {
        // 查询所有品牌
        List<Brand> brands = brandService.findAll();

        // 响应结果封装
        return new Result<List<Brand>>(true, StatusCode.OK, "查询品牌集合成功!", brands);
    }
}
