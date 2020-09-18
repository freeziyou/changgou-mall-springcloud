package com.changgou.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/18/2020 13:47
 * @description TODO
 */
public interface SpecService {

    /**
     * Spec 多条件分页查询
     *
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Spec spec, int page, int size);

    /**
     * Spec 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(int page, int size);

    /**
     * Spec 多条件搜索方法
     *
     * @param spec
     * @return
     */
    List<Spec> findList(Spec spec);

    /**
     * 删除Spec
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改Spec数据
     *
     * @param spec
     */
    void update(Spec spec);

    /**
     * 新增Spec
     *
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据 ID 查询Spec
     *
     * @param id
     * @return
     */
    Spec findById(Integer id);

    /**
     * 查询所有 Spec
     *
     * @return
     */
    List<Spec> findAll();
}
