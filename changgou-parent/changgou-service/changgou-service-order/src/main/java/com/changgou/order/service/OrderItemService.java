package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface OrderItemService {

    /**
     * OrderItem 多条件分页查询
     *
     * @param orderItem
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderItem> findPage(OrderItem orderItem, int page, int size);

    /**
     * OrderItem 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderItem> findPage(int page, int size);

    /**
     * OrderItem 多条件搜索方法
     *
     * @param orderItem
     * @return
     */
    List<OrderItem> findList(OrderItem orderItem);

    /**
     * 删除 OrderItem
     *
     * @param id
     */
    void delete(String id);

    /**
     * 修改 OrderItem 数据
     *
     * @param orderItem
     */
    void update(OrderItem orderItem);

    /**
     * 新增 OrderItem
     *
     * @param orderItem
     */
    void add(OrderItem orderItem);

    /**
     * 根据 ID 查询 OrderItem
     *
     * @param id
     * @return
     */
    OrderItem findById(String id);

    /**
     * 查询所有 OrderItem
     *
     * @return
     */
    List<OrderItem> findAll();
}
