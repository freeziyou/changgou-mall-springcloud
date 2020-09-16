package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Dylan Guo
 * @date 9/15/2020 21:12
 * @description TODO
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    /**
     * 加载启动类，以启动类为当前SpringBoot的配置标准
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
