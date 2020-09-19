package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface BrandService {

    /**
     * 根据分类查询品牌 Id
     *
     * @param categoryId 分类 Id
     * @return
     */
    List<Brand> findByCategory(Integer categoryId);

    /**
     * Brand 多条件分页查询
     *
     * @param brand
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Brand brand, int page, int size);

    /**
     * Brand 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(int page, int size);

    /**
     * Brand 多条件搜索方法
     *
     * @param brand
     * @return
     */
    List<Brand> findList(Brand brand);

    /**
     * 删除 Brand
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Brand 数据
     *
     * @param brand
     */
    void update(Brand brand);

    /**
     * 新增 Brand
     *
     * @param brand
     */
    void add(Brand brand);

    /**
     * 根据 ID 查询 Brand
     *
     * @param id
     * @return
     */
    Brand findById(Integer id);

    /**
     * 查询所有 Brand
     *
     * @return
     */
    List<Brand> findAll();
}
