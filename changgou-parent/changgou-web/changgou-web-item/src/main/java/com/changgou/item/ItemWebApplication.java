package com.changgou.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Dylan Guo
 * @date 9/27/2020 09:46
 * @description TODO
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.changgou.goods.feign")
public class ItemWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemWebApplication.class, args);
    }
}
