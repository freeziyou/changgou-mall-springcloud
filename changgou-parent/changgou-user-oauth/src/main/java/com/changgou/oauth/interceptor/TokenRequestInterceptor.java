package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dylan Guo
 * @date 10/10/2020 19:12
 * @description TODO
 */
@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {

    /**
     * feign 执行之前进行拦截
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        // 生成 admin 令牌
        String token = AdminToken.adminToken();
        template.header("Authorization", "bearer " + token);
    }
}
