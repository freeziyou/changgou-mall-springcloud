package com.changgou.user.service.impl;

import com.changgou.user.dao.OauthClientDetailsMapper;
import com.changgou.user.pojo.OauthClientDetails;
import com.changgou.user.service.OauthClientDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    /**
     * OauthClientDetails 条件分页查询
     * @param oauthClientDetails 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OauthClientDetails> findPage(OauthClientDetails oauthClientDetails, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(oauthClientDetails);
        // 执行搜索
        return new PageInfo<OauthClientDetails>(oauthClientDetailsMapper.selectByExample(example));
    }

    /**
     * OauthClientDetails 分页查询
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<OauthClientDetails> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<OauthClientDetails>(oauthClientDetailsMapper.selectAll());
    }

    /**
     * OauthClientDetails 条件查询
     * @param oauthClientDetails
     * @return
     */
    @Override
    public List<OauthClientDetails> findList(OauthClientDetails oauthClientDetails) {
        // 构建查询条件
        Example example = createExample(oauthClientDetails);
        // 根据构建的条件查询数据
        return oauthClientDetailsMapper.selectByExample(example);
    }


    /**
     * OauthClientDetails  构建查询对象
     * @param oauthClientDetails
     * @return
     */
    public Example createExample(OauthClientDetails oauthClientDetails) {
        Example example = new Example(OauthClientDetails.class);
        Example.Criteria criteria = example.createCriteria();
        if (oauthClientDetails != null){
            // 客户端ID，主要用于标识对应的应用
            if (!StringUtils.isEmpty(oauthClientDetails.getClientId())) {
                criteria.andEqualTo("clientId", oauthClientDetails.getClientId());
            }
            // 
            if (!StringUtils.isEmpty(oauthClientDetails.getResourceIds())) {
                criteria.andEqualTo("resourceIds", oauthClientDetails.getResourceIds());
            }
            // 客户端秘钥，BCryptPasswordEncoder加密算法加密
            if (!StringUtils.isEmpty(oauthClientDetails.getClientSecret())) {
                criteria.andEqualTo("clientSecret", oauthClientDetails.getClientSecret());
            }
            // 对应的范围
            if (!StringUtils.isEmpty(oauthClientDetails.getScope())) {
                criteria.andEqualTo("scope", oauthClientDetails.getScope());
            }
            // 认证模式
            if (!StringUtils.isEmpty(oauthClientDetails.getAuthorizedGrantTypes())) {
                criteria.andEqualTo("authorizedGrantTypes", oauthClientDetails.getAuthorizedGrantTypes());
            }
            // 认证后重定向地址
            if (!StringUtils.isEmpty(oauthClientDetails.getWebServerRedirectUri())) {
                criteria.andEqualTo("webServerRedirectUri", oauthClientDetails.getWebServerRedirectUri());
            }
            // 
            if (!StringUtils.isEmpty(oauthClientDetails.getAuthorities())) {
                criteria.andEqualTo("authorities", oauthClientDetails.getAuthorities());
            }
            // 令牌有效期
            if (!StringUtils.isEmpty(oauthClientDetails.getAccessTokenValidity())) {
                criteria.andEqualTo("accessTokenValidity", oauthClientDetails.getAccessTokenValidity());
            }
            // 令牌刷新周期
            if (!StringUtils.isEmpty(oauthClientDetails.getRefreshTokenValidity())) {
                criteria.andEqualTo("refreshTokenValidity", oauthClientDetails.getRefreshTokenValidity());
            }
            // 
            if (!StringUtils.isEmpty(oauthClientDetails.getAdditionalInformation())) {
                criteria.andEqualTo("additionalInformation", oauthClientDetails.getAdditionalInformation());
            }
            // 
            if (!StringUtils.isEmpty(oauthClientDetails.getAutoapprove())) {
                criteria.andEqualTo("autoapprove", oauthClientDetails.getAutoapprove());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        oauthClientDetailsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 OauthClientDetails
     * @param oauthClientDetails
     */
    @Override
    public void update(OauthClientDetails oauthClientDetails) {
        oauthClientDetailsMapper.updateByPrimaryKey(oauthClientDetails);
    }

    /**
     * 增加 OauthClientDetails
     * @param oauthClientDetails
     */
    @Override
    public void add(OauthClientDetails oauthClientDetails) {
        oauthClientDetailsMapper.insert(oauthClientDetails);
    }

    /**
     * 根据 ID 查询 OauthClientDetails
     * @param id
     * @return
     */
    @Override
    public OauthClientDetails findById(String id) {
        return oauthClientDetailsMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 OauthClientDetails 全部数据
     * @return
     */
    @Override
    public List<OauthClientDetails> findAll() {
        return oauthClientDetailsMapper.selectAll();
    }
}
