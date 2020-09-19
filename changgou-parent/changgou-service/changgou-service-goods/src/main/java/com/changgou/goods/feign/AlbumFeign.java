package com.changgou.goods.feign;

import com.changgou.goods.pojo.Album;
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
@RequestMapping("/album")
public interface AlbumFeign {

    /**
     * Album 分页条件搜索实现
     *
     * @param album
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    Result<PageInfo> findPage(@RequestBody(required = false) Album album, @PathVariable int page, @PathVariable int size);

    /**
     * Album 分页搜索实现
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
     * @param album
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Album>> findList(@RequestBody(required = false) Album album);

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    Result delete(@PathVariable Long id);

    /**
     * 修改 Album数据
     *
     * @param album
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    Result update(@RequestBody Album album, @PathVariable Long id);

    /**
     * 新增 Album 数据
     *
     * @param album
     * @return
     */
    @PostMapping
    Result add(@RequestBody Album album);

    /**
     * 根据 ID 查询 Album 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Album> findById(@PathVariable Long id);

    /**
     * 查询 Album 全部数据
     *
     * @return
     */
    @GetMapping
    Result<List<Album>> findAll();
}