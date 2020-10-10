package com.changgou.filter;

/**
 * @author Dylan Guo
 * @date 10/10/2020 23:29
 * @description 不需要认证就能访问的路径校验
 */
public class URLFilter {
    private static final String ALL_URL = "/api/user/login,/api/user/add";

    /**
     * 校验当前访问路径是否需要权限
     *
     * @param url 获取到的当前的请求的地址
     * @return
     */
    public static boolean hasAuthorize(String url) {
        String[] urls = ALL_URL.split(",");

        for (String uri : urls) {
            if (uri.equals(url)) {
                return true;
            }
        }
        return false;
    }
}
