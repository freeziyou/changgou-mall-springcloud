package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface SpuService {

    /**
     * 还原被删除商品
     *
     * @param spuId
     */
    void restore(Long spuId);

    /**
     * 逻辑删除
     *
     * @param spuId
     */
    void logicDelete(Long spuId);

    /**
     * 批量下架
     *
     * @param ids
     * @return
     */
    int pullMany(Long[] ids);

    /**
     * 批量上架
     *
     * @param ids
     * @return
     */
    int putMany(Long[] ids);

    /**
     * 商品上架
     *
     * @param spuId
     */
    void put(Long spuId);

    /**
     * 商品下架
     *
     * @param spuId
     */
    void pull(Long spuId);

    /**
     * 商品审核
     *
     * @param spuId
     */
    void audit(Long spuId);

    /**
     * 根据 SPU 的 ID 查找 SPU 以及对应的 SKU 集合
     *
     * @param spuId
     */
    Goods findGoodsById(Long spuId);

    /**
     * 保存商品
     *
     * @param goods
     */
    void saveGoods(Goods goods);

    /**
     * Spu 多条件分页查询
     *
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /**
     * Spu 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /**
     * Spu 多条件搜索方法
     *
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /**
     * 删除 Spu
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 Spu 数据
     *
     * @param spu
     */
    void update(Spu spu);

    /**
     * 新增 Spu
     *
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据 ID 查询 Spu
     *
     * @param id
     * @return
     */
    Spu findById(Long id);

    /**
     * 查询所有 Spu
     *
     * @return
     */
    List<Spu> findAll();
}
