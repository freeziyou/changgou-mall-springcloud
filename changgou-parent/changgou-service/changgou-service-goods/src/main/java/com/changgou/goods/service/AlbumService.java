package com.changgou.goods.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface AlbumService {

    /**
     * Album 多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Album album, int page, int size);

    /**
     * Album 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(int page, int size);

    /**
     * Album 多条件搜索方法
     *
     * @param album
     * @return
     */
    List<Album> findList(Album album);

    /**
     * 删除 Album
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 Album 数据
     *
     * @param album
     */
    void update(Album album);

    /**
     * 新增 Album
     *
     * @param album
     */
    void add(Album album);

    /**
     * 根据 ID 查询 Album
     *
     * @param id
     * @return
     */
    Album findById(Long id);

    /**
     * 查询所有 Album
     *
     * @return
     */
    List<Album> findAll();
}
