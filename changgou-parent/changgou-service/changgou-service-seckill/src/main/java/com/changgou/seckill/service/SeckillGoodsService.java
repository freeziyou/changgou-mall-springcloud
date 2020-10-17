package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface SeckillGoodsService {

    /**
     * 根据时间和秒杀商品ID查询秒杀商品数据
     *
     * @param time
     * @param id
     * @return
     */
    SeckillGoods one(String time, Long id);

    /**
     * 根据时间区间查询秒杀商品频道列表数据
     *
     * @param time
     */
    List<SeckillGoods> list(String time);

    /**
     * SeckillGoods 多条件分页查询
     *
     * @param seckillGoods
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillGoods> findPage(SeckillGoods seckillGoods, int page, int size);

    /**
     * SeckillGoods 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillGoods> findPage(int page, int size);

    /**
     * SeckillGoods 多条件搜索方法
     *
     * @param seckillGoods
     * @return
     */
    List<SeckillGoods> findList(SeckillGoods seckillGoods);

    /**
     * 删除 SeckillGoods
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 SeckillGoods 数据
     *
     * @param seckillGoods
     */
    void update(SeckillGoods seckillGoods);

    /**
     * 新增 SeckillGoods
     *
     * @param seckillGoods
     */
    void add(SeckillGoods seckillGoods);

    /**
     * 根据 ID 查询 SeckillGoods
     *
     * @param id
     * @return
     */
    SeckillGoods findById(Long id);

    /**
     * 查询所有 SeckillGoods
     *
     * @return
     */
    List<SeckillGoods> findAll();
}
