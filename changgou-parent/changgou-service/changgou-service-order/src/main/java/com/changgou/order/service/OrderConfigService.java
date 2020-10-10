package com.changgou.order.service;

import com.changgou.order.pojo.OrderConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface OrderConfigService {

    /**
     * OrderConfig 多条件分页查询
     *
     * @param orderConfig
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderConfig> findPage(OrderConfig orderConfig, int page, int size);

    /**
     * OrderConfig 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderConfig> findPage(int page, int size);

    /**
     * OrderConfig 多条件搜索方法
     *
     * @param orderConfig
     * @return
     */
    List<OrderConfig> findList(OrderConfig orderConfig);

    /**
     * 删除 OrderConfig
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 OrderConfig 数据
     *
     * @param orderConfig
     */
    void update(OrderConfig orderConfig);

    /**
     * 新增 OrderConfig
     *
     * @param orderConfig
     */
    void add(OrderConfig orderConfig);

    /**
     * 根据 ID 查询 OrderConfig
     *
     * @param id
     * @return
     */
    OrderConfig findById(Integer id);

    /**
     * 查询所有 OrderConfig
     *
     * @return
     */
    List<OrderConfig> findAll();
}
