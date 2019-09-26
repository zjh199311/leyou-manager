package com.leyou.order.config;

import com.leyou.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author river
 * @title: IdWorkerConfig
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2114:15
 */
@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {

    @Autowired
    private  IdWorkerProperties idWorkerProperties;

    @Bean
    public IdWorker idWorker(){
         return  new IdWorker(idWorkerProperties.getWorkerId(),idWorkerProperties.getDataCenterId());
    }
}
