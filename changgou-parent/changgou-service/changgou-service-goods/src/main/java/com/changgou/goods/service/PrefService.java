package com.changgou.goods.service;

import com.changgou.goods.pojo.Pref;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface PrefService {

    /**
     * Pref 多条件分页查询
     *
     * @param pref
     * @param page
     * @param size
     * @return
     */
    PageInfo<Pref> findPage(Pref pref, int page, int size);

    /**
     * Pref 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Pref> findPage(int page, int size);

    /**
     * Pref 多条件搜索方法
     *
     * @param pref
     * @return
     */
    List<Pref> findList(Pref pref);

    /**
     * 删除 Pref
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Pref 数据
     *
     * @param pref
     */
    void update(Pref pref);

    /**
     * 新增 Pref
     *
     * @param pref
     */
    void add(Pref pref);

    /**
     * 根据 ID 查询 Pref
     *
     * @param id
     * @return
     */
    Pref findById(Integer id);

    /**
     * 查询所有 Pref
     *
     * @return
     */
    List<Pref> findAll();
}
