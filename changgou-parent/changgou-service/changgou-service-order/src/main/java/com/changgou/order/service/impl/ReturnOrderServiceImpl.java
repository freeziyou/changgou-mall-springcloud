package com.changgou.order.service.impl;

import com.changgou.order.dao.ReturnOrderMapper;
import com.changgou.order.pojo.ReturnOrder;
import com.changgou.order.service.ReturnOrderService;
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
public class ReturnOrderServiceImpl implements ReturnOrderService {

    @Autowired
    private ReturnOrderMapper returnOrderMapper;

    /**
     * ReturnOrder 条件分页查询
     *
     * @param returnOrder 查询条件
     * @param page        页码
     * @param size        页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ReturnOrder> findPage(ReturnOrder returnOrder, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(returnOrder);
        // 执行搜索
        return new PageInfo<ReturnOrder>(returnOrderMapper.selectByExample(example));
    }

    /**
     * ReturnOrder 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ReturnOrder> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<ReturnOrder>(returnOrderMapper.selectAll());
    }

    /**
     * ReturnOrder 条件查询
     *
     * @param returnOrder
     * @return
     */
    @Override
    public List<ReturnOrder> findList(ReturnOrder returnOrder) {
        // 构建查询条件
        Example example = createExample(returnOrder);
        // 根据构建的条件查询数据
        return returnOrderMapper.selectByExample(example);
    }


    /**
     * ReturnOrder  构建查询对象
     *
     * @param returnOrder
     * @return
     */
    public Example createExample(ReturnOrder returnOrder) {
        Example example = new Example(ReturnOrder.class);
        Example.Criteria criteria = example.createCriteria();
        if (returnOrder != null) {
            // 服务单号
            if (!StringUtils.isEmpty(returnOrder.getId())) {
                criteria.andEqualTo("id", returnOrder.getId());
            }
            // 订单号
            if (!StringUtils.isEmpty(returnOrder.getOrderId())) {
                criteria.andEqualTo("orderId", returnOrder.getOrderId());
            }
            // 申请时间
            if (!StringUtils.isEmpty(returnOrder.getApplyTime())) {
                criteria.andEqualTo("applyTime", returnOrder.getApplyTime());
            }
            // 用户ID
            if (!StringUtils.isEmpty(returnOrder.getUserId())) {
                criteria.andEqualTo("userId", returnOrder.getUserId());
            }
            // 用户账号
            if (!StringUtils.isEmpty(returnOrder.getUserAccount())) {
                criteria.andEqualTo("userAccount", returnOrder.getUserAccount());
            }
            // 联系人
            if (!StringUtils.isEmpty(returnOrder.getLinkman())) {
                criteria.andEqualTo("linkman", returnOrder.getLinkman());
            }
            // 联系人手机
            if (!StringUtils.isEmpty(returnOrder.getLinkmanMobile())) {
                criteria.andEqualTo("linkmanMobile", returnOrder.getLinkmanMobile());
            }
            // 类型
            if (!StringUtils.isEmpty(returnOrder.getType())) {
                criteria.andEqualTo("type", returnOrder.getType());
            }
            // 退款金额
            if (!StringUtils.isEmpty(returnOrder.getReturnMoney())) {
                criteria.andEqualTo("returnMoney", returnOrder.getReturnMoney());
            }
            // 是否退运费
            if (!StringUtils.isEmpty(returnOrder.getIsReturnFreight())) {
                criteria.andEqualTo("isReturnFreight", returnOrder.getIsReturnFreight());
            }
            // 申请状态
            if (!StringUtils.isEmpty(returnOrder.getStatus())) {
                criteria.andEqualTo("status", returnOrder.getStatus());
            }
            // 处理时间
            if (!StringUtils.isEmpty(returnOrder.getDisposeTime())) {
                criteria.andEqualTo("disposeTime", returnOrder.getDisposeTime());
            }
            // 退货退款原因
            if (!StringUtils.isEmpty(returnOrder.getReturnCause())) {
                criteria.andEqualTo("returnCause", returnOrder.getReturnCause());
            }
            // 凭证图片
            if (!StringUtils.isEmpty(returnOrder.getEvidence())) {
                criteria.andEqualTo("evidence", returnOrder.getEvidence());
            }
            // 问题描述
            if (!StringUtils.isEmpty(returnOrder.getDescription())) {
                criteria.andEqualTo("description", returnOrder.getDescription());
            }
            // 处理备注
            if (!StringUtils.isEmpty(returnOrder.getRemark())) {
                criteria.andEqualTo("remark", returnOrder.getRemark());
            }
            // 管理员id
            if (!StringUtils.isEmpty(returnOrder.getAdminId())) {
                criteria.andEqualTo("adminId", returnOrder.getAdminId());
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
        returnOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 ReturnOrder
     *
     * @param returnOrder
     */
    @Override
    public void update(ReturnOrder returnOrder) {
        returnOrderMapper.updateByPrimaryKey(returnOrder);
    }

    /**
     * 增加 ReturnOrder
     *
     * @param returnOrder
     */
    @Override
    public void add(ReturnOrder returnOrder) {
        returnOrderMapper.insert(returnOrder);
    }

    /**
     * 根据 ID 查询 ReturnOrder
     *
     * @param id
     * @return
     */
    @Override
    public ReturnOrder findById(Long id) {
        return returnOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 ReturnOrder 全部数据
     *
     * @return
     */
    @Override
    public List<ReturnOrder> findAll() {
        return returnOrderMapper.selectAll();
    }
}
