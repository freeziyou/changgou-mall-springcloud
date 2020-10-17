package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillOrder;
import com.github.pagehelper.PageInfo;
import entity.SeckillStatus;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface SeckillOrderService {

    /**
     * 状态查询
     *
     * @param username
     * @return
     */
    SeckillStatus queryStatus(String username);

    /**
     * SeckillOrder 多条件分页查询
     *
     * @param seckillOrder
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size);

    /**
     * SeckillOrder 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(int page, int size);

    /**
     * SeckillOrder 多条件搜索方法
     *
     * @param seckillOrder
     * @return
     */
    List<SeckillOrder> findList(SeckillOrder seckillOrder);

    /**
     * 删除 SeckillOrder
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 SeckillOrder 数据
     *
     * @param seckillOrder
     */
    void update(SeckillOrder seckillOrder);

    /**
     * 根据 ID 查询 SeckillOrder
     *
     * @param id
     * @return
     */
    SeckillOrder findById(Long id);

    /**
     * 查询所有 SeckillOrder
     *
     * @return
     */
    List<SeckillOrder> findAll();

    /**
     * 秒杀下单
     *
     * @param time
     * @param id
     * @param username
     */
    Boolean add(String time, Long id, String username);
}
