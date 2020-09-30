package com.changgou.user.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "oauth_client_details")
public class OauthClientDetails implements Serializable {

    /**
     * 客户端ID，主要用于标识对应的应用
     */
    @Id
    @Column(name = "client_id")
    private String clientId;

    /**
     *
     */
    @Column(name = "resource_ids")
    private String resourceIds;

    /**
     * 客户端秘钥，BCryptPasswordEncoder加密算法加密
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 对应的范围
     */
    @Column(name = "scope")
    private String scope;

    /**
     * 认证模式
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 认证后重定向地址
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     *
     */
    @Column(name = "authorities")
    private String authorities;

    /**
     * 令牌有效期
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 令牌刷新周期
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     *
     */
    @Column(name = "additional_information")
    private String additionalInformation;

    /**
     *
     */
    @Column(name = "autoapprove")
    private String autoapprove;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

}
