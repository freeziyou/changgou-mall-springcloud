package com.changgou.goods.service;

import com.changgou.goods.pojo.Sku;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface SkuService {

    /**
     * Sku 多条件分页查询
     *
     * @param sku
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> findPage(Sku sku, int page, int size);

    /**
     * Sku 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> findPage(int page, int size);

    /**
     * Sku 多条件搜索方法
     *
     * @param sku
     * @return
     */
    List<Sku> findList(Sku sku);

    /**
     * 删除 Sku
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 Sku 数据
     *
     * @param sku
     */
    void update(Sku sku);

    /**
     * 新增 Sku
     *
     * @param sku
     */
    void add(Sku sku);

    /**
     * 根据 ID 查询 Sku
     *
     * @param id
     * @return
     */
    Sku findById(Long id);

    /**
     * 查询所有 Sku
     *
     * @return
     */
    List<Sku> findAll();
}
