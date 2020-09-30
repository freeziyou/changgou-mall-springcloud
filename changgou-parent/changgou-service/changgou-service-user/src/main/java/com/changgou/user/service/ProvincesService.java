package com.changgou.user.service;

import com.changgou.user.pojo.Provinces;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ProvincesService {

    /**
     * Provinces 多条件分页查询
     * @param provinces
     * @param page
     * @param size
     * @return
     */
    PageInfo<Provinces> findPage(Provinces provinces, int page, int size);

    /**
     * Provinces 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Provinces> findPage(int page, int size);

    /**
     * Provinces 多条件搜索方法
     * @param provinces
     * @return
     */
    List<Provinces> findList(Provinces provinces);

    /**
     * 删除 Provinces
     * @param id
     */
    void delete(String id);

    /**
     * 修改 Provinces 数据
     * @param provinces
     */
    void update(Provinces provinces);

    /**
     * 新增 Provinces
     * @param provinces
     */
    void add(Provinces provinces);

    /**
     * 根据 ID 查询 Provinces
     * @param id
     * @return
     */
     Provinces findById(String id);

    /**
     * 查询所有 Provinces
     * @return
     */
    List<Provinces> findAll();
}
