package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.CategoryService;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 恢复数据
     *
     * @param spuId
     */
    @Override
    public void restore(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 检查是否删除的商品
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("此商品未删除！");
        }
        // 未删除
        spu.setIsDelete("0");
        // 未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 逻辑删除
     *
     * @param spuId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logicDelete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 检查是否下架的商品
        if (!"0".equals(spu.getIsMarketable())) {
            throw new RuntimeException("必须先下架再删除！");
        }
        // 删除
        spu.setIsDelete("1");
        // 未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 批量下架
     *
     * @param ids 需要下架的商品 ID 集合
     * @return
     */
    @Override
    public int pullMany(Long[] ids) {
        Spu spu = new Spu();
        // 下架
        spu.setIsMarketable("0");
        // 批量修改
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // id
        criteria.andIn("id", Arrays.asList(ids));
        // 已上架
        criteria.andEqualTo("isMarketable", "1");
        // 已审核
        criteria.andEqualTo("status", "1");
        // 未删除
        criteria.andEqualTo("isDelete", "0");

        return spuMapper.updateByExampleSelective(spu, example);
    }

    /**
     * 批量上架
     *
     * @param ids 需要上架的商品ID集合
     * @return
     */
    @Override
    public int putMany(Long[] ids) {
        Spu spu = new Spu();
        // 上架
        spu.setIsMarketable("1");
        // 批量修改
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // id
        criteria.andIn("id", Arrays.asList(ids));
        // 下架
        criteria.andEqualTo("isMarketable", "0");
        // 已审核
        criteria.andEqualTo("status", "1");
        // 未删除
        criteria.andEqualTo("isDelete", "0");

        return spuMapper.updateByExampleSelective(spu, example);
    }

    /**
     * 商品上架
     *
     * @param spuId
     */
    @Override
    public void put(Long spuId) {
        Spu spu = getSpu(spuId);

        if (!"1".equals(spu.getStatus())) {
            throw new RuntimeException("未通过审核的商品不能上架!");
        }
        //上架状态
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品下架
     *
     * @param spuId
     */
    @Override
    public void pull(Long spuId) {
        Spu spu = getSpu(spuId);
        // 下架状态
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品审核
     *
     * @param spuId
     */
    @Override
    public void audit(Long spuId) {
        Spu spu = getSpu(spuId);
        // 实现审核
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 审核上下架判断是否已被删除
     *
     * @param spuId
     * @return
     */
    private Spu getSpu(Long spuId) {
        // 查询商品
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断是否已经被删除
        if ("1".equalsIgnoreCase(spu.getIsDelete())) {
            throw new RuntimeException("该商品已被删除!");
        }
        return spu;
    }

    /**
     * 根据 SpuID 查询 goods 信息
     *
     * @param spuId
     * @return
     */
    @Override
    public Goods findGoodsById(Long spuId) {
        // 查询 Spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        // 查询 sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        // 封装 goods
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skus);
        return goods;
    }

    /**
     * 添加商品信息
     *
     * @param goods 商品
     */
    @Override
    public void saveGoods(Goods goods) {
        // 增加 Spu
        Spu spu = goods.getSpu();
        if (spu.getId() == null) {
            // 增加数据
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else {
            // 修改数据
            spuMapper.updateByPrimaryKeySelective(spu);
            // 删除该 Spu 的 Sku
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        // 增加 Sku
        Date date = new Date();
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        // 获取 Sku 集合
        List<Sku> skuList = goods.getSkuList();

        // 构建 Sku 名称, 采用 SPU + 规格值组装
        for (Sku sku : skuList) {
            // 空值处理
            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");
            }

            // 获取 spec 的值
            String name = spu.getName();
            // 将 spec 转换成 map
            Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                name += " " + entry.getValue();
            }

            sku.setName(name);
            sku.setId(idWorker.nextId());
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setSpuId(spu.getId());
            sku.setCategoryId(spu.getCategory3Id());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());

            // 将 Sku 添加到数据库中
            skuMapper.insertSelective(sku);
        }
    }

    /**
     * Spu 条件分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(spu);
        // 执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu 条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        // 构建查询条件
        Example example = createExample(spu);
        // 根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu 构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架,0已下架，1已上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除,0:未删除，1：已删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态，0：未审核，1：已审核，2：审核不通过
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
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
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //检查是否被逻辑删除  ,必须先逻辑删除后才能物理删除
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("此商品不能删除！");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Spu
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加 Spu
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据 ID 查询 Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Spu 全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }
}
