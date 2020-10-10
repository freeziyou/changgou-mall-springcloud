package com.changgou.filter;

import com.changgou.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Dylan Guo
 * @date 9/30/2020 16:26
 * @description 全局过滤器，实现用户鉴权
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    /**
     * 令牌 header 名字
     */
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /**
     * 全局拦截
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 用户如果是登录或者一些不需要权限认证的请求，直接放行
        String uri = request.getURI().toString();
        if (URLFilter.hasAuthorize(uri)) {
            return chain.filter(exchange);
        }

        // header 中获取用户令牌信息
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // hasToken true：令牌在头文件中，false不在头文件中->将令牌封装到头文件中，再传递给其他微服务
        boolean hasToken = true;

        // 参数中获取用户令牌信息
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken = false;
        }

        // cookie 中获取用户令牌信息
        if (StringUtils.isEmpty(token)) {
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (httpCookie != null) {
                token = httpCookie.getValue();
            }
        }

        // 令牌为空，则允许访问，直接拦截
        if (StringUtils.isEmpty(token)) {
            // 设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        } else {
            if (!hasToken) {
                // 判断当前令牌是否有 bearer 前缀，如果没有则添加
                if (!token.startsWith("bearer ") && !token.startsWith("Bearer ")) {
                    token = "bearer " + token;
                }
                // 将令牌封装到头文件中
                request.mutate().header(AUTHORIZE_TOKEN, token);
            }
        }

        // 有效则放行
        return chain.filter(exchange);
    }

    /**
     * 排序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
