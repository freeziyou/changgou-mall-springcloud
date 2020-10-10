package com.changgou.order.service;

import com.changgou.order.pojo.CategoryReport;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface CategoryReportService {

    /**
     * CategoryReport 多条件分页查询
     *
     * @param categoryReport
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryReport> findPage(CategoryReport categoryReport, int page, int size);

    /**
     * CategoryReport 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryReport> findPage(int page, int size);

    /**
     * CategoryReport 多条件搜索方法
     *
     * @param categoryReport
     * @return
     */
    List<CategoryReport> findList(CategoryReport categoryReport);

    /**
     * 删除 CategoryReport
     *
     * @param id
     */
    void delete(Date id);

    /**
     * 修改 CategoryReport 数据
     *
     * @param categoryReport
     */
    void update(CategoryReport categoryReport);

    /**
     * 新增 CategoryReport
     *
     * @param categoryReport
     */
    void add(CategoryReport categoryReport);

    /**
     * 根据 ID 查询 CategoryReport
     *
     * @param id
     * @return
     */
    CategoryReport findById(Date id);

    /**
     * 查询所有 CategoryReport
     *
     * @return
     */
    List<CategoryReport> findAll();
}
