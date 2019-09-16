package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author river
 * @title: LeyouUserApplication
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2910:27
 */
@SpringBootApplication
@EnableDiscoveryClient  // 开启EurekaClient功能
@MapperScan("com.leyou.user.mapper")
@ComponentScan("com.leyou.user")
@EnableFeignClients  //启用feign进行远程调用
//@EnableConfigServer
public class LeyouUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUserApplication.class, args);
    }
}