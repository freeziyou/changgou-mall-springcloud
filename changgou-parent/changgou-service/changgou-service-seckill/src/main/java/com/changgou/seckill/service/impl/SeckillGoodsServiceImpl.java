package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SeckillGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据时间和秒杀商品ID查询秒杀商品数据
     *
     * @param time
     * @param id
     * @return
     */
    @Override
    public SeckillGoods one(String time, Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);
    }

    /**
     * 根据时间区间查询秒杀商品频道列表数据
     *
     * @param time
     */
    @Override
    public List<SeckillGoods> list(String time) {
        return redisTemplate.boundHashOps("SeckillGoods_" + time).values();
    }

    /**
     * SeckillGoods 条件分页查询
     *
     * @param seckillGoods 查询条件
     * @param page         页码
     * @param size         页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillGoods> findPage(SeckillGoods seckillGoods, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(seckillGoods);
        // 执行搜索
        return new PageInfo<SeckillGoods>(seckillGoodsMapper.selectByExample(example));
    }

    /**
     * SeckillGoods 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillGoods> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<SeckillGoods>(seckillGoodsMapper.selectAll());
    }

    /**
     * SeckillGoods 条件查询
     *
     * @param seckillGoods
     * @return
     */
    @Override
    public List<SeckillGoods> findList(SeckillGoods seckillGoods) {
        // 构建查询条件
        Example example = createExample(seckillGoods);
        // 根据构建的条件查询数据
        return seckillGoodsMapper.selectByExample(example);
    }


    /**
     * SeckillGoods  构建查询对象
     *
     * @param seckillGoods
     * @return
     */
    public Example createExample(SeckillGoods seckillGoods) {
        Example example = new Example(SeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        if (seckillGoods != null) {
            // 
            if (!StringUtils.isEmpty(seckillGoods.getId())) {
                criteria.andEqualTo("id", seckillGoods.getId());
            }
            // spu ID
            if (!StringUtils.isEmpty(seckillGoods.getSupId())) {
                criteria.andEqualTo("supId", seckillGoods.getSupId());
            }
            // sku ID
            if (!StringUtils.isEmpty(seckillGoods.getSkuId())) {
                criteria.andEqualTo("skuId", seckillGoods.getSkuId());
            }
            // 标题
            if (!StringUtils.isEmpty(seckillGoods.getName())) {
                criteria.andLike("name", "%" + seckillGoods.getName() + "%");
            }
            // 商品图片
            if (!StringUtils.isEmpty(seckillGoods.getSmallPic())) {
                criteria.andEqualTo("smallPic", seckillGoods.getSmallPic());
            }
            // 原价格
            if (!StringUtils.isEmpty(seckillGoods.getPrice())) {
                criteria.andEqualTo("price", seckillGoods.getPrice());
            }
            // 秒杀价格
            if (!StringUtils.isEmpty(seckillGoods.getCostPrice())) {
                criteria.andEqualTo("costPrice", seckillGoods.getCostPrice());
            }
            // 添加日期
            if (!StringUtils.isEmpty(seckillGoods.getCreateTime())) {
                criteria.andEqualTo("createTime", seckillGoods.getCreateTime());
            }
            // 审核日期
            if (!StringUtils.isEmpty(seckillGoods.getCheckTime())) {
                criteria.andEqualTo("checkTime", seckillGoods.getCheckTime());
            }
            // 审核状态，0未审核，1审核通过，2审核不通过
            if (!StringUtils.isEmpty(seckillGoods.getStatus())) {
                criteria.andEqualTo("status", seckillGoods.getStatus());
            }
            // 开始时间
            if (!StringUtils.isEmpty(seckillGoods.getStartTime())) {
                criteria.andEqualTo("startTime", seckillGoods.getStartTime());
            }
            // 结束时间
            if (!StringUtils.isEmpty(seckillGoods.getEndTime())) {
                criteria.andEqualTo("endTime", seckillGoods.getEndTime());
            }
            // 秒杀商品数
            if (!StringUtils.isEmpty(seckillGoods.getNum())) {
                criteria.andEqualTo("num", seckillGoods.getNum());
            }
            // 剩余库存数
            if (!StringUtils.isEmpty(seckillGoods.getStockCount())) {
                criteria.andEqualTo("stockCount", seckillGoods.getStockCount());
            }
            // 描述
            if (!StringUtils.isEmpty(seckillGoods.getIntroduction())) {
                criteria.andEqualTo("introduction", seckillGoods.getIntroduction());
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
        seckillGoodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 SeckillGoods
     *
     * @param seckillGoods
     */
    @Override
    public void update(SeckillGoods seckillGoods) {
        seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
    }

    /**
     * 增加 SeckillGoods
     *
     * @param seckillGoods
     */
    @Override
    public void add(SeckillGoods seckillGoods) {
        seckillGoodsMapper.insert(seckillGoods);
    }

    /**
     * 根据 ID 查询 SeckillGoods
     *
     * @param id
     * @return
     */
    @Override
    public SeckillGoods findById(Long id) {
        return seckillGoodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 SeckillGoods 全部数据
     *
     * @return
     */
    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsMapper.selectAll();
    }
}
