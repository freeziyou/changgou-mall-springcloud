package com.changgou.order.service;

import com.changgou.order.pojo.OrderLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface OrderLogService {

    /**
     * OrderLog 多条件分页查询
     *
     * @param orderLog
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderLog> findPage(OrderLog orderLog, int page, int size);

    /**
     * OrderLog 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderLog> findPage(int page, int size);

    /**
     * OrderLog 多条件搜索方法
     *
     * @param orderLog
     * @return
     */
    List<OrderLog> findList(OrderLog orderLog);

    /**
     * 删除 OrderLog
     *
     * @param id
     */
    void delete(String id);

    /**
     * 修改 OrderLog 数据
     *
     * @param orderLog
     */
    void update(OrderLog orderLog);

    /**
     * 新增 OrderLog
     *
     * @param orderLog
     */
    void add(OrderLog orderLog);

    /**
     * 根据 ID 查询 OrderLog
     *
     * @param id
     * @return
     */
    OrderLog findById(String id);

    /**
     * 查询所有 OrderLog
     *
     * @return
     */
    List<OrderLog> findAll();
}
