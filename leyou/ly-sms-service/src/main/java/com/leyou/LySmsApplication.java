package com.leyou;

import com.leyou.sms.properties.SmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author river
 * @title: LySmsApplication
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2311:19
 */

@SpringBootApplication
public class LySmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LySmsApplication.class);
    }
}
