package com.changgou.content.service;

import com.changgou.content.pojo.Content;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ContentService {

    /**
     * 根据 categoryId 查询广告集合
     * @param id
     * @return
     */
    List<Content> findByCategory(Long id);

    /**
     * Content 多条件分页查询
     *
     * @param content
     * @param page
     * @param size
     * @return
     */
    PageInfo<Content> findPage(Content content, int page, int size);

    /**
     * Content 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Content> findPage(int page, int size);

    /**
     * Content 多条件搜索方法
     *
     * @param content
     * @return
     */
    List<Content> findList(Content content);

    /**
     * 删除 Content
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 Content 数据
     *
     * @param content
     */
    void update(Content content);

    /**
     * 新增 Content
     *
     * @param content
     */
    void add(Content content);

    /**
     * 根据 ID 查询 Content
     *
     * @param id
     * @return
     */
    Content findById(Long id);

    /**
     * 查询所有 Content
     *
     * @return
     */
    List<Content> findAll();
}
