package com.changgou.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:00
 * @description TODO
 */
public interface AlbumService {
    /**
     * Album多条件分页查询
     *
     * @param album Album
     * @param page  当前页
     * @param size  每页显示条数
     * @return PageInfo
     */
    PageInfo<Album> findPage(Album album, int page, int size);

    /**
     * Album分页查询
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return PageInfo
     */
    PageInfo<Album> findPage(int page, int size);

    /**
     * Album多条件搜索方法
     *
     * @param album Album
     * @return Album 列表
     */
    List<Album> findList(Album album);

    /**
     * 删除Album
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 修改Album数据
     *
     * @param album Album
     */
    void update(Album album);

    /**
     * 新增Album
     *
     * @param album
     */
    void add(Album album);

    /**
     * 根据 ID 查询 Album
     *
     * @param id id
     * @return Album
     */
    Album findById(Long id);

    /**
     * 查询所有 Album
     *
     * @return Album 列表
     */
    List<Album> findAll();
}
