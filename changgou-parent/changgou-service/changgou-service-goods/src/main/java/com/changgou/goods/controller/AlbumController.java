package com.changgou.goods.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
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
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /**
     * Album 分页条件搜索实现
     *
     * @param album
     * @param page  当前页
     * @param size  每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Album album, @PathVariable int page, @PathVariable int size) {
        // 调用 AlbumService 实现分页条件查询 Album
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Album 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 AlbumService 实现分页查询 Album
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param album
     * @return
     */
    @PostMapping("/search")
    public Result<List<Album>> findList(@RequestBody(required = false) Album album) {
        //调用 AlbumService 实现条件查询 Album
        List<Album> list = albumService.findList(album);
        return new Result<List<Album>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 调用 AlbumService 实现根据主键删除
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Album数据
     *
     * @param album
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Album album, @PathVariable Long id) {
        // 设置主键值
        album.setId(id);
        // 调用 AlbumService 实现修改 Album
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Album 数据
     *
     * @param album
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Album album) {
        // 调用 AlbumService 实现添加 Album
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Album 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable Long id) {
        // 调用 AlbumService 实现根据主键查询 Album
        Album album = albumService.findById(id);
        return new Result<Album>(true, StatusCode.OK, "ID 查询成功!", album);
    }

    /**
     * 查询 Album 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Album>> findAll() {
        // 调用 AlbumService 实现查询所有 Album
        List<Album> list = albumService.findAll();
        return new Result<List<Album>>(true, StatusCode.OK, "查询成功!", list);
    }
}
