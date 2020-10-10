package com.changgou.order.service.impl;

import com.changgou.order.dao.OrderLogMapper;
import com.changgou.order.pojo.OrderLog;
import com.changgou.order.service.OrderLogService;
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
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * OrderLog 条件分页查询
     *
     * @param orderLog 查询条件
     * @param page     页码
     * @param size     页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderLog> findPage(OrderLog orderLog, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(orderLog);
        // 执行搜索
        return new PageInfo<OrderLog>(orderLogMapper.selectByExample(example));
    }

    /**
     * OrderLog 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderLog> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<OrderLog>(orderLogMapper.selectAll());
    }

    /**
     * OrderLog 条件查询
     *
     * @param orderLog
     * @return
     */
    @Override
    public List<OrderLog> findList(OrderLog orderLog) {
        // 构建查询条件
        Example example = createExample(orderLog);
        // 根据构建的条件查询数据
        return orderLogMapper.selectByExample(example);
    }


    /**
     * OrderLog  构建查询对象
     *
     * @param orderLog
     * @return
     */
    public Example createExample(OrderLog orderLog) {
        Example example = new Example(OrderLog.class);
        Example.Criteria criteria = example.createCriteria();
        if (orderLog != null) {
            // ID
            if (!StringUtils.isEmpty(orderLog.getId())) {
                criteria.andEqualTo("id", orderLog.getId());
            }
            // 操作员
            if (!StringUtils.isEmpty(orderLog.getOperater())) {
                criteria.andEqualTo("operater", orderLog.getOperater());
            }
            // 操作时间
            if (!StringUtils.isEmpty(orderLog.getOperateTime())) {
                criteria.andEqualTo("operateTime", orderLog.getOperateTime());
            }
            // 订单ID
            if (!StringUtils.isEmpty(orderLog.getOrderId())) {
                criteria.andEqualTo("orderId", orderLog.getOrderId());
            }
            // 订单状态,0未完成，1已完成，2，已退货
            if (!StringUtils.isEmpty(orderLog.getOrderStatus())) {
                criteria.andEqualTo("orderStatus", orderLog.getOrderStatus());
            }
            // 付款状态  0:未支付，1：已支付，2：支付失败
            if (!StringUtils.isEmpty(orderLog.getPayStatus())) {
                criteria.andEqualTo("payStatus", orderLog.getPayStatus());
            }
            // 发货状态
            if (!StringUtils.isEmpty(orderLog.getConsignStatus())) {
                criteria.andEqualTo("consignStatus", orderLog.getConsignStatus());
            }
            // 备注
            if (!StringUtils.isEmpty(orderLog.getRemarks())) {
                criteria.andEqualTo("remarks", orderLog.getRemarks());
            }
            // 支付金额
            if (!StringUtils.isEmpty(orderLog.getMoney())) {
                criteria.andEqualTo("money", orderLog.getMoney());
            }
            // 
            if (!StringUtils.isEmpty(orderLog.getUsername())) {
                criteria.andLike("username", "%" + orderLog.getUsername() + "%");
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
    public void delete(String id) {
        orderLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 OrderLog
     *
     * @param orderLog
     */
    @Override
    public void update(OrderLog orderLog) {
        orderLogMapper.updateByPrimaryKey(orderLog);
    }

    /**
     * 增加 OrderLog
     *
     * @param orderLog
     */
    @Override
    public void add(OrderLog orderLog) {
        orderLogMapper.insert(orderLog);
    }

    /**
     * 根据 ID 查询 OrderLog
     *
     * @param id
     * @return
     */
    @Override
    public OrderLog findById(String id) {
        return orderLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 OrderLog 全部数据
     *
     * @return
     */
    @Override
    public List<OrderLog> findAll() {
        return orderLogMapper.selectAll();
    }
}
