package com.changgou.seckill.task;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import entity.IdWorker;
import entity.SeckillStatus;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dylan Guo
 * @date 10/17/2020 15:09
 * @description TODO
 */
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void createOrder() {
        try {
            System.out.println("等待一会再下单！");
            Thread.sleep(10000);

            // 从 redis 队列中获取用户排队信息
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();
            if (seckillStatus == null) {
                return;
            }

            // 定义要购买商品的信息
            String time = seckillStatus.getTime();
            Long id = seckillStatus.getGoodsId();
            String username = seckillStatus.getUsername();

            // 先到 SeckillGoodsCountList_ID 队列中获取商品信息，能获取则可下单
            Object sgoods = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();
            // 如不能获取，则代表无库存，清理排队信息
            if (sgoods == null) {
                clearUserQueue(username);
                return;
            }

            // 查询秒杀商品
            String namespace = "SeckillGoods_" + time;
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(namespace).get(id);

            // 判断有没有库存
            if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
                throw new RuntimeException("已售罄!");
            }

            // 创建订单对象
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setSeckillId(id);
            seckillOrder.setMoney(seckillGoods.getCostPrice());
            seckillOrder.setUserId(username);
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setStatus("0");

            // 将订单对象存储起来
            redisTemplate.boundHashOps("SeckillOrder").put(username, seckillOrder);

            // 库存递减
            seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
            Thread.sleep(10000);
            // 获取商品对应的队列数量
            Long size = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).size();


            if (size <= 0) {
                // 同步数量
                seckillGoods.setStockCount(size.intValue());
                // 同步数据到 mysql
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                // 删除 redis 中的数据
                redisTemplate.boundHashOps(namespace).delete(id);
            } else {
                // 同步数据到 redis
                redisTemplate.boundHashOps(namespace).put(id, seckillGoods);
            }

            // 更新下单状态
            seckillStatus.setOrderId(seckillOrder.getId());
            seckillStatus.setMoney(Float.valueOf(seckillGoods.getCostPrice()));
            seckillStatus.setStatus(2);
            redisTemplate.boundHashOps("UserQueueStatus").put(username, seckillStatus);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("下单时间："+ simpleDateFormat.format(new Date()));

            // 发送消息给延时队列
            rabbitTemplate.convertAndSend("delaySeckillQueue", (Object) JSON.toJSONString(seckillStatus), new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                }
            });

            System.out.println("下单成功！");
        } catch (Exception e) {
            e.printStackTrace();
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
}
