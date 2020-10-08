package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.LoginService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grandType) {

        // 1.定义url (申请令牌的url)
        // 获取指定服务的注册信息
        ServiceInstance choose = loadBalancerClient.choose("user-auth");
        String url = choose.getUri().toString() + "/oauth/token";

        // 2.定义头信息 (有client id 和client 秘钥)
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(new String(clientId + ":" + clientSecret).getBytes()));
        // 3. 定义请求体  有授权模式 用户的名称 和密码
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grandType);
        formData.add("username", username);
        formData.add("password", password);
        // 4.模拟浏览器 发送POST 请求 携带 头 和请求体 到认证服务器

        /**
         * 参数1  指定要发送的请求的url
         * 参数2  指定要发送的请求的方法 PSOT
         * 参数3 指定请求实体(包含头和请求体数据)
         */
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(formData, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        // 5.接收到返回的响应(就是:令牌的信息)
        Map<String, String> map = responseEntity.getBody();

        // 处理返回的信息
        AuthToken authToken = new AuthToken();

        // jti，作为用户的身份标识
        authToken.setJti(map.get("jti"));
        // 访问令牌(jwt)
        authToken.setAccessToken(map.get("access_token"));
        // 刷新令牌(jwt)
        authToken.setRefreshToken(map.get("refresh_token"));

        return authToken;
    }


    public static void main(String[] args) {
        byte[] decode = Base64.getDecoder().decode(new String("Y2hhbmdnb3UxOmNoYW5nZ291Mg==").getBytes());
        System.out.println(new String(decode));
    }
}
