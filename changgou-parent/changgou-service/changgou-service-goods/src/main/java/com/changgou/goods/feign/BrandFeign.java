package com.changgou.goods.feign;

import com.changgou.goods.pojo.Brand;
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
@RequestMapping("/brand")
public interface BrandFeign {

    /**
     * Brand 分页条件搜索实现
     *
     * @param brand
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Brand brand, @PathVariable int page, @PathVariable int size);

    /**
     * Brand 分页搜索实现
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
     * @param brand
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Brand>> findList(@RequestBody(required = false) Brand brand);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Integer id);

    /**
     * 修改 Brand数据
     *
     * @param brand
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Brand brand, @PathVariable Integer id);

    /**
     * 新增 Brand 数据
     *
     * @param brand
     * @return
     */
    @PostMapping
    Result add(@RequestBody Brand brand);

    /**
     * 根据 ID 查询 Brand 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Brand> findById(@PathVariable Integer id);

    /**
     * 查询 Brand 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Brand>> findAll();
}