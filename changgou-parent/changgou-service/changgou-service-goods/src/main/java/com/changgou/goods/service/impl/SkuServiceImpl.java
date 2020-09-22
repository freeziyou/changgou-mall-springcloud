package com.changgou.goods.service.impl;

import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.service.SkuService;
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
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 根据状态查询 SKU 列表
     *
     * @param status
     * @return
     */
    @Override
    public List<Sku> findByStatus(String status) {
        Sku sku = new Sku();
        sku.setStatus(status);
        return skuMapper.select(sku);
    }

    /**
     * Sku 条件分页查询
     *
     * @param sku  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Sku> findPage(Sku sku, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(sku);
        // 执行搜索
        return new PageInfo<Sku>(skuMapper.selectByExample(example));
    }

    /**
     * Sku 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Sku> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Sku>(skuMapper.selectAll());
    }

    /**
     * Sku 条件查询
     *
     * @param sku
     * @return
     */
    @Override
    public List<Sku> findList(Sku sku) {
        // 构建查询条件
        Example example = createExample(sku);
        // 根据构建的条件查询数据
        return skuMapper.selectByExample(example);
    }


    /**
     * Sku  构建查询对象
     *
     * @param sku
     * @return
     */
    public Example createExample(Sku sku) {
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if (sku != null) {
            // 商品id
            if (!StringUtils.isEmpty(sku.getId())) {
                criteria.andEqualTo("id", sku.getId());
            }
            // 商品条码
            if (!StringUtils.isEmpty(sku.getSn())) {
                criteria.andEqualTo("sn", sku.getSn());
            }
            // SKU名称
            if (!StringUtils.isEmpty(sku.getName())) {
                criteria.andLike("name", "%" + sku.getName() + "%");
            }
            // 价格（分）
            if (!StringUtils.isEmpty(sku.getPrice())) {
                criteria.andEqualTo("price", sku.getPrice());
            }
            // 库存数量
            if (!StringUtils.isEmpty(sku.getNum())) {
                criteria.andEqualTo("num", sku.getNum());
            }
            // 库存预警数量
            if (!StringUtils.isEmpty(sku.getAlertNum())) {
                criteria.andEqualTo("alertNum", sku.getAlertNum());
            }
            // 商品图片
            if (!StringUtils.isEmpty(sku.getImage())) {
                criteria.andEqualTo("image", sku.getImage());
            }
            // 商品图片列表
            if (!StringUtils.isEmpty(sku.getImages())) {
                criteria.andEqualTo("images", sku.getImages());
            }
            // 重量（克）
            if (!StringUtils.isEmpty(sku.getWeight())) {
                criteria.andEqualTo("weight", sku.getWeight());
            }
            // 创建时间
            if (!StringUtils.isEmpty(sku.getCreateTime())) {
                criteria.andEqualTo("createTime", sku.getCreateTime());
            }
            // 更新时间
            if (!StringUtils.isEmpty(sku.getUpdateTime())) {
                criteria.andEqualTo("updateTime", sku.getUpdateTime());
            }
            // SPUID
            if (!StringUtils.isEmpty(sku.getSpuId())) {
                criteria.andEqualTo("spuId", sku.getSpuId());
            }
            // 类目ID
            if (!StringUtils.isEmpty(sku.getCategoryId())) {
                criteria.andEqualTo("categoryId", sku.getCategoryId());
            }
            // 类目名称
            if (!StringUtils.isEmpty(sku.getCategoryName())) {
                criteria.andEqualTo("categoryName", sku.getCategoryName());
            }
            // 品牌名称
            if (!StringUtils.isEmpty(sku.getBrandName())) {
                criteria.andEqualTo("brandName", sku.getBrandName());
            }
            // 规格
            if (!StringUtils.isEmpty(sku.getSpec())) {
                criteria.andEqualTo("spec", sku.getSpec());
            }
            // 销量
            if (!StringUtils.isEmpty(sku.getSaleNum())) {
                criteria.andEqualTo("saleNum", sku.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(sku.getCommentNum())) {
                criteria.andEqualTo("commentNum", sku.getCommentNum());
            }
            // 商品状态 1-正常，2-下架，3-删除
            if (!StringUtils.isEmpty(sku.getStatus())) {
                criteria.andEqualTo("status", sku.getStatus());
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
        skuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Sku
     *
     * @param sku
     */
    @Override
    public void update(Sku sku) {
        skuMapper.updateByPrimaryKey(sku);
    }

    /**
     * 增加 Sku
     *
     * @param sku
     */
    @Override
    public void add(Sku sku) {
        skuMapper.insert(sku);
    }

    /**
     * 根据 ID 查询 Sku
     *
     * @param id
     * @return
     */
    @Override
    public Sku findById(Long id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Sku 全部数据
     *
     * @return
     */
    @Override
    public List<Sku> findAll() {
        return skuMapper.selectAll();
    }
}
