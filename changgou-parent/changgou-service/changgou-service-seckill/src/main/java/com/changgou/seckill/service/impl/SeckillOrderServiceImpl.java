package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SeckillOrderService;
import com.changgou.seckill.task.MultiThreadingCreateOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 删除订单
     *
     * @param username
     */
    @Override
    public void deleteOrder(String username) {
        // 删除订单
        redisTemplate.boundHashOps("SeckillOrder").delete(username);
        // 查询用户排队信息
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
        // 删除排队信息
        clearUserQueue(username);


        // 回滚库存->redis 递增-> redis 不一定有商品
        String namespace = "SeckillGoods_" + seckillStatus.getTime();
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(namespace).get(seckillStatus.getGoodsId());

        // 如果商品为空
        if (seckillGoods == null) {
            // 数据库中查询
            seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillStatus.getGoodsId());
            // 更新数据库库存
            seckillGoods.setStockCount(1);
            seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
        } else {
            seckillGoods.setStockCount(seckillGoods.getStockCount() + 1);
        }
        redisTemplate.boundHashOps(namespace).put(seckillGoods.getId(), seckillGoods);

        // 队列
        redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillGoods.getId()).leftPush(seckillGoods.getId());
    }

    /**
     * 修改秒杀订单状态
     *
     * @param username
     * @param transactionid
     * @param endtime
     */
    @Override
    public void updatePayStatus(String username, String transactionid, String endtime) {
        // 查询订单
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);
        if (seckillOrder != null) {
            try {
                // 修改订单状态信息
                seckillOrder.setStatus("1");
                seckillOrder.setTransactionId(transactionid);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date payTimeInfo = simpleDateFormat.parse(endtime);
                seckillOrder.setPayTime(payTimeInfo);
                seckillOrderMapper.insertSelective(seckillOrder);

                // 删除 redis 中的订单
                redisTemplate.boundHashOps("SeckillOrder").delete(username);

                // 删除用户排队信息
                clearUserQueue(username);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清理用户排队抢单信息
     *
     * @param username
     */
    public void clearUserQueue(String username) {
        // 排队标识
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        // 排队信息清理
        redisTemplate.boundHashOps("UserQueueStatus").delete(username);
    }

    /**
     * 抢单状态查询
     *
     * @param username
     * @return
     */
    @Override
    public SeckillStatus queryStatus(String username) {
        return (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
    }

    /**
     * 秒杀下单-排队
     *
     * @param time
     * @param id
     * @param username
     * @return
     */
    @Override
    public Boolean add(String time, Long id, String username) {
        // 记录用户排队次数
        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(username, 1);
        if (userQueueCount > 1) {
            throw new RuntimeException("重复排队!");
        }

        // 创建排队对象
        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(), 1, id, time);
        // redis 中 list 为队列，有序,SeckillOrderQueue 为用户抢单排队
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(seckillStatus);
        // 用户抢单状态->用于查询
        redisTemplate.boundHashOps("UserQueueStatus").put(username, seckillStatus);

        // 异步执行
        multiThreadingCreateOrder.createOrder();
        return true;
    }

    /**
     * SeckillOrder 条件分页查询
     *
     * @param seckillOrder 查询条件
     * @param page         页码
     * @param size         页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(seckillOrder);
        // 执行搜索
        return new PageInfo<SeckillOrder>(seckillOrderMapper.selectByExample(example));
    }

    /**
     * SeckillOrder 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillOrder> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<SeckillOrder>(seckillOrderMapper.selectAll());
    }

    /**
     * SeckillOrder 条件查询
     *
     * @param seckillOrder
     * @return
     */
    @Override
    public List<SeckillOrder> findList(SeckillOrder seckillOrder) {
        // 构建查询条件
        Example example = createExample(seckillOrder);
        // 根据构建的条件查询数据
        return seckillOrderMapper.selectByExample(example);
    }

    /**
     * SeckillOrder  构建查询对象
     *
     * @param seckillOrder
     * @return
     */
    public Example createExample(SeckillOrder seckillOrder) {
        Example example = new Example(SeckillOrder.class);
        Example.Criteria criteria = example.createCriteria();
        if (seckillOrder != null) {
            // 主键
            if (!StringUtils.isEmpty(seckillOrder.getId())) {
                criteria.andEqualTo("id", seckillOrder.getId());
            }
            // 秒杀商品ID
            if (!StringUtils.isEmpty(seckillOrder.getSeckillId())) {
                criteria.andEqualTo("seckillId", seckillOrder.getSeckillId());
            }
            // 支付金额
            if (!StringUtils.isEmpty(seckillOrder.getMoney())) {
                criteria.andEqualTo("money", seckillOrder.getMoney());
            }
            // 用户
            if (!StringUtils.isEmpty(seckillOrder.getUserId())) {
                criteria.andEqualTo("userId", seckillOrder.getUserId());
            }
            // 创建时间
            if (!StringUtils.isEmpty(seckillOrder.getCreateTime())) {
                criteria.andEqualTo("createTime", seckillOrder.getCreateTime());
            }
            // 支付时间
            if (!StringUtils.isEmpty(seckillOrder.getPayTime())) {
                criteria.andEqualTo("payTime", seckillOrder.getPayTime());
            }
            // 状态，0未支付，1已支付
            if (!StringUtils.isEmpty(seckillOrder.getStatus())) {
                criteria.andEqualTo("status", seckillOrder.getStatus());
            }
            // 收货人地址
            if (!StringUtils.isEmpty(seckillOrder.getReceiverAddress())) {
                criteria.andEqualTo("receiverAddress", seckillOrder.getReceiverAddress());
            }
            // 收货人电话
            if (!StringUtils.isEmpty(seckillOrder.getReceiverMobile())) {
                criteria.andEqualTo("receiverMobile", seckillOrder.getReceiverMobile());
            }
            // 收货人
            if (!StringUtils.isEmpty(seckillOrder.getReceiver())) {
                criteria.andEqualTo("receiver", seckillOrder.getReceiver());
            }
            // 交易流水
            if (!StringUtils.isEmpty(seckillOrder.getTransactionId())) {
                criteria.andEqualTo("transactionId", seckillOrder.getTransactionId());
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
        seckillOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 SeckillOrder
     *
     * @param seckillOrder
     */
    @Override
    public void update(SeckillOrder seckillOrder) {
        seckillOrderMapper.updateByPrimaryKey(seckillOrder);
    }

    /**
     * 根据 ID 查询 SeckillOrder
     *
     * @param id
     * @return
     */
    @Override
    public SeckillOrder findById(Long id) {
        return seckillOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 SeckillOrder 全部数据
     *
     * @return
     */
    @Override
    public List<SeckillOrder> findAll() {
        return seckillOrderMapper.selectAll();
    }


}
