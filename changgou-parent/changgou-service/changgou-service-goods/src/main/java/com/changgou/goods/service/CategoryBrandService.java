package com.changgou.goods.service;

import com.changgou.goods.pojo.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface CategoryBrandService {

    /**
     * CategoryBrand 多条件分页查询
     *
     * @param categoryBrand
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryBrand> findPage(CategoryBrand categoryBrand, int page, int size);

    /**
     * CategoryBrand 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryBrand> findPage(int page, int size);

    /**
     * CategoryBrand 多条件搜索方法
     *
     * @param categoryBrand
     * @return
     */
    List<CategoryBrand> findList(CategoryBrand categoryBrand);

    /**
     * 删除 CategoryBrand
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 CategoryBrand 数据
     *
     * @param categoryBrand
     */
    void update(CategoryBrand categoryBrand);

    /**
     * 新增 CategoryBrand
     *
     * @param categoryBrand
     */
    void add(CategoryBrand categoryBrand);

    /**
     * 根据 ID 查询 CategoryBrand
     *
     * @param id
     * @return
     */
    CategoryBrand findById(Integer id);

    /**
     * 查询所有 CategoryBrand
     *
     * @return
     */
    List<CategoryBrand> findAll();
}
