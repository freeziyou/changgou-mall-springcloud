package com.changgou.order.service;

import com.changgou.order.pojo.Preferential;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface PreferentialService {

    /**
     * Preferential 多条件分页查询
     *
     * @param preferential
     * @param page
     * @param size
     * @return
     */
    PageInfo<Preferential> findPage(Preferential preferential, int page, int size);

    /**
     * Preferential 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Preferential> findPage(int page, int size);

    /**
     * Preferential 多条件搜索方法
     *
     * @param preferential
     * @return
     */
    List<Preferential> findList(Preferential preferential);

    /**
     * 删除 Preferential
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Preferential 数据
     *
     * @param preferential
     */
    void update(Preferential preferential);

    /**
     * 新增 Preferential
     *
     * @param preferential
     */
    void add(Preferential preferential);

    /**
     * 根据 ID 查询 Preferential
     *
     * @param id
     * @return
     */
    Preferential findById(Integer id);

    /**
     * 查询所有 Preferential
     *
     * @return
     */
    List<Preferential> findAll();
}
