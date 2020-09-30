package com.changgou.user.service;

import com.changgou.user.pojo.Cities;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface CitiesService {

    /**
     * Cities 多条件分页查询
     * @param cities
     * @param page
     * @param size
     * @return
     */
    PageInfo<Cities> findPage(Cities cities, int page, int size);

    /**
     * Cities 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Cities> findPage(int page, int size);

    /**
     * Cities 多条件搜索方法
     * @param cities
     * @return
     */
    List<Cities> findList(Cities cities);

    /**
     * 删除 Cities
     * @param id
     */
    void delete(String id);

    /**
     * 修改 Cities 数据
     * @param cities
     */
    void update(Cities cities);

    /**
     * 新增 Cities
     * @param cities
     */
    void add(Cities cities);

    /**
     * 根据 ID 查询 Cities
     * @param id
     * @return
     */
     Cities findById(String id);

    /**
     * 查询所有 Cities
     * @return
     */
    List<Cities> findAll();
}
