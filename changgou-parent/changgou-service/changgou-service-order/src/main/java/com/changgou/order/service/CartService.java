package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 10/10/2020 13:29
 * @description TODO
 */
public interface CartService {

    /**
     * 加入购物车实现
     * @param num
     * @param id
     */
    void add(Integer num, Long id, String username);

    /**
     * 购物车集合查询
     * @param username
     * @return
     */
    List<OrderItem> list(String username);
}
