package com.changgou.goods.service;

import com.changgou.goods.pojo.StockBack;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface StockBackService {

    /**
     * StockBack 多条件分页查询
     *
     * @param stockBack
     * @param page
     * @param size
     * @return
     */
    PageInfo<StockBack> findPage(StockBack stockBack, int page, int size);

    /**
     * StockBack 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<StockBack> findPage(int page, int size);

    /**
     * StockBack 多条件搜索方法
     *
     * @param stockBack
     * @return
     */
    List<StockBack> findList(StockBack stockBack);

    /**
     * 删除 StockBack
     *
     * @param id
     */
    void delete(String id);

    /**
     * 修改 StockBack 数据
     *
     * @param stockBack
     */
    void update(StockBack stockBack);

    /**
     * 新增 StockBack
     *
     * @param stockBack
     */
    void add(StockBack stockBack);

    /**
     * 根据 ID 查询 StockBack
     *
     * @param id
     * @return
     */
    StockBack findById(String id);

    /**
     * 查询所有 StockBack
     *
     * @return
     */
    List<StockBack> findAll();
}
