package com.changgou.order.service.impl;

import com.changgou.order.dao.OrderConfigMapper;
import com.changgou.order.pojo.OrderConfig;
import com.changgou.order.service.OrderConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Service
public class OrderConfigServiceImpl implements OrderConfigService {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    /**
     * OrderConfig 条件分页查询
     *
     * @param orderConfig 查询条件
     * @param page        页码
     * @param size        页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderConfig> findPage(OrderConfig orderConfig, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(orderConfig);
        // 执行搜索
        return new PageInfo<OrderConfig>(orderConfigMapper.selectByExample(example));
    }

    /**
     * OrderConfig 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderConfig> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<OrderConfig>(orderConfigMapper.selectAll());
    }

    /**
     * OrderConfig 条件查询
     *
     * @param orderConfig
     * @return
     */
    @Override
    public List<OrderConfig> findList(OrderConfig orderConfig) {
        // 构建查询条件
        Example example = createExample(orderConfig);
        // 根据构建的条件查询数据
        return orderConfigMapper.selectByExample(example);
    }


    /**
     * OrderConfig  构建查询对象
     *
     * @param orderConfig
     * @return
     */
    public Example createExample(OrderConfig orderConfig) {
        Example example = new Example(OrderConfig.class);
        Example.Criteria criteria = example.createCriteria();
        if (orderConfig != null) {
            // ID
            if (!StringUtils.isEmpty(orderConfig.getId())) {
                criteria.andEqualTo("id", orderConfig.getId());
            }
            // 正常订单超时时间（分）
            if (!StringUtils.isEmpty(orderConfig.getOrderTimeout())) {
                criteria.andEqualTo("orderTimeout", orderConfig.getOrderTimeout());
            }
            // 秒杀订单超时时间（分）
            if (!StringUtils.isEmpty(orderConfig.getSeckillTimeout())) {
                criteria.andEqualTo("seckillTimeout", orderConfig.getSeckillTimeout());
            }
            // 自动收货（天）
            if (!StringUtils.isEmpty(orderConfig.getTakeTimeout())) {
                criteria.andEqualTo("takeTimeout", orderConfig.getTakeTimeout());
            }
            // 售后期限
            if (!StringUtils.isEmpty(orderConfig.getServiceTimeout())) {
                criteria.andEqualTo("serviceTimeout", orderConfig.getServiceTimeout());
            }
            // 自动五星好评
            if (!StringUtils.isEmpty(orderConfig.getCommentTimeout())) {
                criteria.andEqualTo("commentTimeout", orderConfig.getCommentTimeout());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        orderConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 OrderConfig
     *
     * @param orderConfig
     */
    @Override
    public void update(OrderConfig orderConfig) {
        orderConfigMapper.updateByPrimaryKey(orderConfig);
    }

    /**
     * 增加 OrderConfig
     *
     * @param orderConfig
     */
    @Override
    public void add(OrderConfig orderConfig) {
        orderConfigMapper.insert(orderConfig);
    }

    /**
     * 根据 ID 查询 OrderConfig
     *
     * @param id
     * @return
     */
    @Override
    public OrderConfig findById(Integer id) {
        return orderConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 OrderConfig 全部数据
     *
     * @return
     */
    @Override
    public List<OrderConfig> findAll() {
        return orderConfigMapper.selectAll();
    }
}
