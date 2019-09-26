package com.leyou.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: IdWorkerProperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2114:11
 */
@Data
@ConfigurationProperties(prefix = "leyou.worker")
@Component
public class IdWorkerProperties {
    private  Long workerId; // 工作机器id
    private  Long dataCenterId; //序列号id
}
