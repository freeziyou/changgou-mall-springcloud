package com.changgou.user.service;

import com.changgou.user.pojo.OauthClientDetails;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface OauthClientDetailsService {

    /**
     * OauthClientDetails 多条件分页查询
     *
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(OauthClientDetails oauthClientDetails, int page, int size);

    /**
     * OauthClientDetails 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(int page, int size);

    /**
     * OauthClientDetails 多条件搜索方法
     *
     * @param oauthClientDetails
     * @return
     */
    List<OauthClientDetails> findList(OauthClientDetails oauthClientDetails);

    /**
     * 删除 OauthClientDetails
     *
     * @param id
     */
    void delete(String id);

    /**
     * 修改 OauthClientDetails 数据
     *
     * @param oauthClientDetails
     */
    void update(OauthClientDetails oauthClientDetails);

    /**
     * 新增 OauthClientDetails
     *
     * @param oauthClientDetails
     */
    void add(OauthClientDetails oauthClientDetails);

    /**
     * 根据 ID 查询 OauthClientDetails
     *
     * @param id
     * @return
     */
    OauthClientDetails findById(String id);

    /**
     * 查询所有 OauthClientDetails
     *
     * @return
     */
    List<OauthClientDetails> findAll();
}
