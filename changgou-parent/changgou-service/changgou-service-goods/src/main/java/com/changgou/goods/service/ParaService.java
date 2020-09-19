package com.changgou.goods.service;

import com.changgou.goods.pojo.Para;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ParaService {

    /**
     * 根据分类 ID 查询参数列表
     * @param id
     * @return
     */
    List<Para> findByCategoryId(Integer id);

    /**
     * Para 多条件分页查询
     *
     * @param para
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Para para, int page, int size);

    /**
     * Para 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(int page, int size);

    /**
     * Para 多条件搜索方法
     *
     * @param para
     * @return
     */
    List<Para> findList(Para para);

    /**
     * 删除 Para
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Para 数据
     *
     * @param para
     */
    void update(Para para);

    /**
     * 新增 Para
     *
     * @param para
     */
    void add(Para para);

    /**
     * 根据 ID 查询 Para
     *
     * @param id
     * @return
     */
    Para findById(Integer id);

    /**
     * 查询所有 Para
     *
     * @return
     */
    List<Para> findAll();
}
