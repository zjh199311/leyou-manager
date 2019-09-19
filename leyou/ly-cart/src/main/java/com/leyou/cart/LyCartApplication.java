package com.leyou.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author river
 * @title: LyCartApplication
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1716:28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyCartApplication.class);
    }
}
