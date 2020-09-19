package com.changgou.goods.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface SpecService {

    /**
     * 根据分类 ID 查询规格列表
     * @param categoryId
     * @return
     */
    List<Spec> findByCategoryId(Integer categoryId);

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
     * 删除 Spec
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Spec 数据
     *
     * @param spec
     */
    void update(Spec spec);

    /**
     * 新增 Spec
     *
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据 ID 查询 Spec
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
