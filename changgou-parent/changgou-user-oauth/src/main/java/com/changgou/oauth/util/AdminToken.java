package com.changgou.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;

/**
 * @author Dylan Guo
 * @date 10/10/2020 19:21
 * @description TODO
 */
public class AdminToken {

    /**
     * 管理员令牌发放
     * @return
     */
    public static String adminToken() {
        // 加载证书，读取类路径中的文件
        ClassPathResource resource = new ClassPathResource("changgou.jks");

        // 读取证书数据，加载读取证书数据
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "changgou".toCharArray());

        // 获取证书中的一对秘钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("changgou", "changgou".toCharArray());

        // 获取私钥->RSA算法
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 创建令牌，需要私钥加盐（RSA算法）
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("nickname","tomcat");
        payload.put("address","sz");
        payload.put("authorities",new String[]{"admin", "oauth"});

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));

        // 获取令牌数据
        String token = jwt.getEncoded();
        return token;
    }
}
