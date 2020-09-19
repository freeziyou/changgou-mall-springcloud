package com.changgou.goods.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface CategoryService {

    /**
     * Category 多条件分页查询
     *
     * @param category
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Category category, int page, int size);

    /**
     * Category 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(int page, int size);

    /**
     * Category 多条件搜索方法
     *
     * @param category
     * @return
     */
    List<Category> findList(Category category);

    /**
     * 删除 Category
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Category 数据
     *
     * @param category
     */
    void update(Category category);

    /**
     * 新增 Category
     *
     * @param category
     */
    void add(Category category);

    /**
     * 根据 ID 查询 Category
     *
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 查询所有 Category
     *
     * @return
     */
    List<Category> findAll();

    /**
     * 根据父节点 ID 查询
     *
     * @param pid 父节点ID
     */
    List<Category> findByParentId(Integer pid);
}
