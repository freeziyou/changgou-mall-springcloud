package com.changgou.user.service;

import com.changgou.user.pojo.Address;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface AddressService {

    /**
     * 根据用户登录名字查询用户收件地址列表信息
     *
     * @param username
     * @return
     */
    List<Address> list(String username);

    /**
     * Address 多条件分页查询
     *
     * @param address
     * @param page
     * @param size
     * @return
     */
    PageInfo<Address> findPage(Address address, int page, int size);

    /**
     * Address 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Address> findPage(int page, int size);

    /**
     * Address 多条件搜索方法
     *
     * @param address
     * @return
     */
    List<Address> findList(Address address);

    /**
     * 删除 Address
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改 Address 数据
     *
     * @param address
     */
    void update(Address address);

    /**
     * 新增 Address
     *
     * @param address
     */
    void add(Address address);

    /**
     * 根据 ID 查询 Address
     *
     * @param id
     * @return
     */
    Address findById(Integer id);

    /**
     * 查询所有 Address
     *
     * @return
     */
    List<Address> findAll();
}
