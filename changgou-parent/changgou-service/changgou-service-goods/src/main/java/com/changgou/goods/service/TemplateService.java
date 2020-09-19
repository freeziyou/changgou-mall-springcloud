package com.changgou.goods.service;

import com.changgou.goods.pojo.Template;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface TemplateService {

    /**
     * 根据分类 ID 查询模板信息
     *
     * @param id
     * @return
     */
    Template findByCategoryId(Integer id);

    /**
     * Template 多条件分页查询
     *
     * @param template
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(Template template, int page, int size);

    /**
     * Template 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(int page, int size);

    /**
     * Template 多条件搜索方法
     *
     * @param template
     * @return
     */
    List<Template> findList(Template template);

    /**
     * 删除 Template
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Template 数据
     *
     * @param template
     */
    void update(Template template);

    /**
     * 新增 Template
     *
     * @param template
     */
    void add(Template template);

    /**
     * 根据 ID 查询 Template
     *
     * @param id
     * @return
     */
    Template findById(Integer id);

    /**
     * 查询所有 Template
     *
     * @return
     */
    List<Template> findAll();
}
