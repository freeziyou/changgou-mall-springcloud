package com.changgou.user.service;

import com.changgou.user.pojo.Areas;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface AreasService {

    /**
     * Areas 多条件分页查询
     * @param areas
     * @param page
     * @param size
     * @return
     */
    PageInfo<Areas> findPage(Areas areas, int page, int size);

    /**
     * Areas 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Areas> findPage(int page, int size);

    /**
     * Areas 多条件搜索方法
     * @param areas
     * @return
     */
    List<Areas> findList(Areas areas);

    /**
     * 删除 Areas
     * @param id
     */
    void delete(String id);

    /**
     * 修改 Areas 数据
     * @param areas
     */
    void update(Areas areas);

    /**
     * 新增 Areas
     * @param areas
     */
    void add(Areas areas);

    /**
     * 根据 ID 查询 Areas
     * @param id
     * @return
     */
     Areas findById(String id);

    /**
     * 查询所有 Areas
     * @return
     */
    List<Areas> findAll();
}
