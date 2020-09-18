package com.changgou.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.service.AlbumService;
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
     * 分页条件查询
     *
     * @param album 搜索条件
     * @param page  当前页
     * @param size  每页显示多少条
     * @return 结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Album album, @PathVariable int page, @PathVariable int size) {
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return new Result(true, StatusCode.OK, "页面条件查询成功!", pageInfo);
    }

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 条件查询
     *
     * @param album 相册
     * @return 符合条件相册
     */
    @PostMapping("/search")
    public Result<List<Album>> findList(@RequestBody(required = false) Album album) {
        List<Album> list = albumService.findList(album);
        return new Result<List<Album>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id id
     * @return 结果消息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 根据 ID 修改相册数据
     *
     * @param album 相册
     * @param id    id
     * @return 结果消息
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Album album, @PathVariable Long id) {
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 添加相册数据
     *
     * @param album 相册
     * @return 结果消息
     */
    @PostMapping()
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查找相册数据
     *
     * @param id id
     * @return 结果消息
     */
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return new Result<Album>(true, StatusCode.OK, "id 查询成功", album);

    }

    /**
     * 查找所有相册数据
     *
     * @return 结果消息
     */
    @GetMapping
    public Result<List<Album>> findAll() {
        List<Album> albumList = albumService.findAll();
        return new Result<List<Album>>(true, StatusCode.OK, "查询成功", albumList);
    }

}
