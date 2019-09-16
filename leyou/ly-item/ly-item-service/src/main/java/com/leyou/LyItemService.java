package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author river
 * @title: LyItemService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/110:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper")
@ComponentScan("com.leyou.item")
@EnableFeignClients
public class LyItemService {
    public static void main(String[] args) {
        SpringApplication.run(LyItemService.class);
    }

}
