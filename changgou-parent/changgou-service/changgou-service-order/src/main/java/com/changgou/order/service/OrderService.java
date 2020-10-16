package com.changgou.order.service;

import com.changgou.order.pojo.Order;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface OrderService {

    /**
     * 删除订单
     *
     * @param outtradeno
     */
    void deleteOrder(String outtradeno);

    /**
     * 修改订单状态
     *
     * @param outtradeno
     * @param paytime
     * @param transactionid
     */
    void updateStatus(String outtradeno, String paytime, String transactionid) throws Exception;

    /**
     * Order 多条件分页查询
     *
     * @param order
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(Order order, int page, int size);

    /**
     * Order 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(int page, int size);

    /**
     * Order 多条件搜索方法
     *
     * @param order
     * @return
     */
    List<Order> findList(Order order);

    /**
     * 删除 Order
     *
     * @param id
     */
    void delete(String id);

    /**
     * 修改 Order 数据
     *
     * @param order
     */
    void update(Order order);

    /**
     * 新增 Order
     *
     * @param order
     * @return
     */
    Order add(Order order);

    /**
     * 根据 ID 查询 Order
     *
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * 查询所有 Order
     *
     * @return
     */
    List<Order> findAll();
}
