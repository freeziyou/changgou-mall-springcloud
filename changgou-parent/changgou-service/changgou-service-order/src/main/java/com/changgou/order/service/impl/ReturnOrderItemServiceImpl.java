package com.changgou.order.service.impl;

import com.changgou.order.dao.ReturnOrderItemMapper;
import com.changgou.order.pojo.ReturnOrderItem;
import com.changgou.order.service.ReturnOrderItemService;
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
public class ReturnOrderItemServiceImpl implements ReturnOrderItemService {

    @Autowired
    private ReturnOrderItemMapper returnOrderItemMapper;

    /**
     * ReturnOrderItem 条件分页查询
     *
     * @param returnOrderItem 查询条件
     * @param page            页码
     * @param size            页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ReturnOrderItem> findPage(ReturnOrderItem returnOrderItem, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(returnOrderItem);
        // 执行搜索
        return new PageInfo<ReturnOrderItem>(returnOrderItemMapper.selectByExample(example));
    }

    /**
     * ReturnOrderItem 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ReturnOrderItem> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<ReturnOrderItem>(returnOrderItemMapper.selectAll());
    }

    /**
     * ReturnOrderItem 条件查询
     *
     * @param returnOrderItem
     * @return
     */
    @Override
    public List<ReturnOrderItem> findList(ReturnOrderItem returnOrderItem) {
        // 构建查询条件
        Example example = createExample(returnOrderItem);
        // 根据构建的条件查询数据
        return returnOrderItemMapper.selectByExample(example);
    }


    /**
     * ReturnOrderItem  构建查询对象
     *
     * @param returnOrderItem
     * @return
     */
    public Example createExample(ReturnOrderItem returnOrderItem) {
        Example example = new Example(ReturnOrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (returnOrderItem != null) {
            // ID
            if (!StringUtils.isEmpty(returnOrderItem.getId())) {
                criteria.andEqualTo("id", returnOrderItem.getId());
            }
            // 分类ID
            if (!StringUtils.isEmpty(returnOrderItem.getCategoryId())) {
                criteria.andEqualTo("categoryId", returnOrderItem.getCategoryId());
            }
            // SPU_ID
            if (!StringUtils.isEmpty(returnOrderItem.getSpuId())) {
                criteria.andEqualTo("spuId", returnOrderItem.getSpuId());
            }
            // SKU_ID
            if (!StringUtils.isEmpty(returnOrderItem.getSkuId())) {
                criteria.andEqualTo("skuId", returnOrderItem.getSkuId());
            }
            // 订单ID
            if (!StringUtils.isEmpty(returnOrderItem.getOrderId())) {
                criteria.andEqualTo("orderId", returnOrderItem.getOrderId());
            }
            // 订单明细ID
            if (!StringUtils.isEmpty(returnOrderItem.getOrderItemId())) {
                criteria.andEqualTo("orderItemId", returnOrderItem.getOrderItemId());
            }
            // 退货订单ID
            if (!StringUtils.isEmpty(returnOrderItem.getReturnOrderId())) {
                criteria.andEqualTo("returnOrderId", returnOrderItem.getReturnOrderId());
            }
            // 标题
            if (!StringUtils.isEmpty(returnOrderItem.getTitle())) {
                criteria.andLike("title", "%" + returnOrderItem.getTitle() + "%");
            }
            // 单价
            if (!StringUtils.isEmpty(returnOrderItem.getPrice())) {
                criteria.andEqualTo("price", returnOrderItem.getPrice());
            }
            // 数量
            if (!StringUtils.isEmpty(returnOrderItem.getNum())) {
                criteria.andEqualTo("num", returnOrderItem.getNum());
            }
            // 总金额
            if (!StringUtils.isEmpty(returnOrderItem.getMoney())) {
                criteria.andEqualTo("money", returnOrderItem.getMoney());
            }
            // 支付金额
            if (!StringUtils.isEmpty(returnOrderItem.getPayMoney())) {
                criteria.andEqualTo("payMoney", returnOrderItem.getPayMoney());
            }
            // 图片地址
            if (!StringUtils.isEmpty(returnOrderItem.getImage())) {
                criteria.andEqualTo("image", returnOrderItem.getImage());
            }
            // 重量
            if (!StringUtils.isEmpty(returnOrderItem.getWeight())) {
                criteria.andEqualTo("weight", returnOrderItem.getWeight());
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
    public void delete(Long id) {
        returnOrderItemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 ReturnOrderItem
     *
     * @param returnOrderItem
     */
    @Override
    public void update(ReturnOrderItem returnOrderItem) {
        returnOrderItemMapper.updateByPrimaryKey(returnOrderItem);
    }

    /**
     * 增加 ReturnOrderItem
     *
     * @param returnOrderItem
     */
    @Override
    public void add(ReturnOrderItem returnOrderItem) {
        returnOrderItemMapper.insert(returnOrderItem);
    }

    /**
     * 根据 ID 查询 ReturnOrderItem
     *
     * @param id
     * @return
     */
    @Override
    public ReturnOrderItem findById(Long id) {
        return returnOrderItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 ReturnOrderItem 全部数据
     *
     * @return
     */
    @Override
    public List<ReturnOrderItem> findAll() {
        return returnOrderItemMapper.selectAll();
    }
}
