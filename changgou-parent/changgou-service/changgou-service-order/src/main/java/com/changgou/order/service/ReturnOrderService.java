package com.changgou.order.service;

import com.changgou.order.pojo.ReturnOrder;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ReturnOrderService {

    /**
     * ReturnOrder 多条件分页查询
     *
     * @param returnOrder
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnOrder> findPage(ReturnOrder returnOrder, int page, int size);

    /**
     * ReturnOrder 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnOrder> findPage(int page, int size);

    /**
     * ReturnOrder 多条件搜索方法
     *
     * @param returnOrder
     * @return
     */
    List<ReturnOrder> findList(ReturnOrder returnOrder);

    /**
     * 删除 ReturnOrder
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 ReturnOrder 数据
     *
     * @param returnOrder
     */
    void update(ReturnOrder returnOrder);

    /**
     * 新增 ReturnOrder
     *
     * @param returnOrder
     */
    void add(ReturnOrder returnOrder);

    /**
     * 根据 ID 查询 ReturnOrder
     *
     * @param id
     * @return
     */
    ReturnOrder findById(Long id);

    /**
     * 查询所有 ReturnOrder
     *
     * @return
     */
    List<ReturnOrder> findAll();
}
