package com.leyou.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: YzmTimeRidesProperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/3013:26
 */
@Component
@ConfigurationProperties(prefix = "leyou.code")
@Data
public class YzmTimeRidesProperties {
    private Integer time;
    private  String admqExchange;
    private  String admqQuence;

}
