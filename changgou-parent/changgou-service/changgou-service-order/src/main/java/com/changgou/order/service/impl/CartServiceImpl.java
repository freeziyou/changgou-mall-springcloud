package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 10/10/2020 13:29
 * @description TODO
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    /**
     * 购物车集合查询
     *
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> list(String username) {
        // 获取指定命名空间下所有数据
        return redisTemplate.boundHashOps("Cart_" + username).values();
    }

    /**
     * 加入购物车
     *
     * @param num
     * @param id
     */
    @Override
    public void add(Integer num, Long id, String username) {
        // 当添加购物车数量 <= 0 时，需要移除该商品
        if (num <= 0) {
            // 移除购物车该商品
            redisTemplate.boundHashOps("Cart_" + username).delete(id);

            // 如果此时购物车数量为空，则连购物车一起移除
            Long size = redisTemplate.boundHashOps("Cart_" + username).size();
            if (size == null || size <= 0) {
                redisTemplate.delete("Cart_" + username);
            }

            return;
        }

        // 查询商品的详情
        // 1. 查询 sku
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();
        // 2. 查询 spu
        Result<Spu> spuResult = spuFeign.findById(sku.getSpuId());
        Spu spu = spuResult.getData();

        OrderItem orderItem = createOrderItem(num, id, sku, spu);

        // 将购物车数据存入到 Redis：namespace->username
        redisTemplate.boundHashOps("Cart_" + username).put(id, orderItem);
    }


    /**
     * 创建一个 orderItem 对象
     *
     * @param num
     * @param id
     * @param sku
     * @param spu
     * @return
     */
    private OrderItem createOrderItem(Integer num, Long id, Sku sku, Spu spu) {
        // 将加入购物车的商品信息封装成 OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(sku.getId());
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num * orderItem.getPrice());
        orderItem.setImage(spu.getImage());
        return orderItem;
    }
}
