package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/16/2020 13:14
 * @description TODO
 */
public interface BrandService {

    /**
     * 分页条件查询
     *
     * @param brand 搜索条件
     * @param page 当前页
     * @param size 每页显示条数
     * @return 结果
     */
    PageInfo<Brand> findPage(Brand brand, int page, int size);

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return 结果
     */
    PageInfo<Brand> findPage(int page, int size);

    /**
     * 多条件搜索品牌方法
     *
     * @param brand 品牌
     * @return 符合查询条件的品牌
     */
    List<Brand> findList(Brand brand);

    /**
     * 删除品牌
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 修改品牌数据
     *
     * @param brand 品牌
     */
    void update(Brand brand);

    /**
     * 增加品牌
     *
     * @param brand 品牌
     */
    void add(Brand brand);

    /**
     * 根据 id 查询
     *
     * @param id id
     * @return 返回结果
     */
    Brand findById(Integer id);

    /**
     * 查询所有
     *
     * @return 所有结果
     */
    List<Brand> findAll();
}
