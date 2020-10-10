package com.changgou.order.service.impl;

import com.changgou.order.dao.OrderItemMapper;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.OrderItemService;
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
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * OrderItem 条件分页查询
     *
     * @param orderItem 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderItem> findPage(OrderItem orderItem, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(orderItem);
        // 执行搜索
        return new PageInfo<OrderItem>(orderItemMapper.selectByExample(example));
    }

    /**
     * OrderItem 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OrderItem> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<OrderItem>(orderItemMapper.selectAll());
    }

    /**
     * OrderItem 条件查询
     *
     * @param orderItem
     * @return
     */
    @Override
    public List<OrderItem> findList(OrderItem orderItem) {
        // 构建查询条件
        Example example = createExample(orderItem);
        // 根据构建的条件查询数据
        return orderItemMapper.selectByExample(example);
    }


    /**
     * OrderItem  构建查询对象
     *
     * @param orderItem
     * @return
     */
    public Example createExample(OrderItem orderItem) {
        Example example = new Example(OrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (orderItem != null) {
            // ID
            if (!StringUtils.isEmpty(orderItem.getId())) {
                criteria.andEqualTo("id", orderItem.getId());
            }
            // 1级分类
            if (!StringUtils.isEmpty(orderItem.getCategoryId1())) {
                criteria.andEqualTo("categoryId1", orderItem.getCategoryId1());
            }
            // 2级分类
            if (!StringUtils.isEmpty(orderItem.getCategoryId2())) {
                criteria.andEqualTo("categoryId2", orderItem.getCategoryId2());
            }
            // 3级分类
            if (!StringUtils.isEmpty(orderItem.getCategoryId3())) {
                criteria.andEqualTo("categoryId3", orderItem.getCategoryId3());
            }
            // SPU_ID
            if (!StringUtils.isEmpty(orderItem.getSpuId())) {
                criteria.andEqualTo("spuId", orderItem.getSpuId());
            }
            // SKU_ID
            if (!StringUtils.isEmpty(orderItem.getSkuId())) {
                criteria.andEqualTo("skuId", orderItem.getSkuId());
            }
            // 订单ID
            if (!StringUtils.isEmpty(orderItem.getOrderId())) {
                criteria.andEqualTo("orderId", orderItem.getOrderId());
            }
            // 商品名称
            if (!StringUtils.isEmpty(orderItem.getName())) {
                criteria.andLike("name", "%" + orderItem.getName() + "%");
            }
            // 单价
            if (!StringUtils.isEmpty(orderItem.getPrice())) {
                criteria.andEqualTo("price", orderItem.getPrice());
            }
            // 数量
            if (!StringUtils.isEmpty(orderItem.getNum())) {
                criteria.andEqualTo("num", orderItem.getNum());
            }
            // 总金额
            if (!StringUtils.isEmpty(orderItem.getMoney())) {
                criteria.andEqualTo("money", orderItem.getMoney());
            }
            // 实付金额
            if (!StringUtils.isEmpty(orderItem.getPayMoney())) {
                criteria.andEqualTo("payMoney", orderItem.getPayMoney());
            }
            // 图片地址
            if (!StringUtils.isEmpty(orderItem.getImage())) {
                criteria.andEqualTo("image", orderItem.getImage());
            }
            // 重量
            if (!StringUtils.isEmpty(orderItem.getWeight())) {
                criteria.andEqualTo("weight", orderItem.getWeight());
            }
            // 运费
            if (!StringUtils.isEmpty(orderItem.getPostFee())) {
                criteria.andEqualTo("postFee", orderItem.getPostFee());
            }
            // 是否退货,0:未退货，1：已退货
            if (!StringUtils.isEmpty(orderItem.getIsReturn())) {
                criteria.andEqualTo("isReturn", orderItem.getIsReturn());
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
        orderItemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 OrderItem
     *
     * @param orderItem
     */
    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKey(orderItem);
    }

    /**
     * 增加 OrderItem
     *
     * @param orderItem
     */
    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    /**
     * 根据 ID 查询 OrderItem
     *
     * @param id
     * @return
     */
    @Override
    public OrderItem findById(String id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 OrderItem 全部数据
     *
     * @return
     */
    @Override
    public List<OrderItem> findAll() {
        return orderItemMapper.selectAll();
    }
}
