package com.changgou.order.service;

import com.changgou.order.pojo.ReturnOrderItem;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ReturnOrderItemService {

    /**
     * ReturnOrderItem 多条件分页查询
     *
     * @param returnOrderItem
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnOrderItem> findPage(ReturnOrderItem returnOrderItem, int page, int size);

    /**
     * ReturnOrderItem 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnOrderItem> findPage(int page, int size);

    /**
     * ReturnOrderItem 多条件搜索方法
     *
     * @param returnOrderItem
     * @return
     */
    List<ReturnOrderItem> findList(ReturnOrderItem returnOrderItem);

    /**
     * 删除 ReturnOrderItem
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 ReturnOrderItem 数据
     *
     * @param returnOrderItem
     */
    void update(ReturnOrderItem returnOrderItem);

    /**
     * 新增 ReturnOrderItem
     *
     * @param returnOrderItem
     */
    void add(ReturnOrderItem returnOrderItem);

    /**
     * 根据 ID 查询 ReturnOrderItem
     *
     * @param id
     * @return
     */
    ReturnOrderItem findById(Long id);

    /**
     * 查询所有 ReturnOrderItem
     *
     * @return
     */
    List<ReturnOrderItem> findAll();
}
