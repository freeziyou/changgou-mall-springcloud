package com.changgou.user.service;

import com.changgou.user.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface UserService {

    /**
     * User 多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

    /**
     * User 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(int page, int size);

    /**
     * User 多条件搜索方法
     * @param user
     * @return
     */
    List<User> findList(User user);

    /**
     * 删除 User
     * @param id
     */
    void delete(String id);

    /**
     * 修改 User 数据
     * @param user
     */
    void update(User user);

    /**
     * 新增 User
     * @param user
     */
    void add(User user);

    /**
     * 根据 ID 查询 User
     * @param id
     * @return
     */
     User findById(String id);

    /**
     * 查询所有 User
     * @return
     */
    List<User> findAll();
}
