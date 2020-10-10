package com.changgou.order.service;

import com.changgou.order.pojo.ReturnCause;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ReturnCauseService {

    /**
     * ReturnCause 多条件分页查询
     *
     * @param returnCause
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnCause> findPage(ReturnCause returnCause, int page, int size);

    /**
     * ReturnCause 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnCause> findPage(int page, int size);

    /**
     * ReturnCause 多条件搜索方法
     *
     * @param returnCause
     * @return
     */
    List<ReturnCause> findList(ReturnCause returnCause);

    /**
     * 删除 ReturnCause
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 ReturnCause 数据
     *
     * @param returnCause
     */
    void update(ReturnCause returnCause);

    /**
     * 新增 ReturnCause
     *
     * @param returnCause
     */
    void add(ReturnCause returnCause);

    /**
     * 根据 ID 查询 ReturnCause
     *
     * @param id
     * @return
     */
    ReturnCause findById(Integer id);

    /**
     * 查询所有 ReturnCause
     *
     * @return
     */
    List<ReturnCause> findAll();
}
