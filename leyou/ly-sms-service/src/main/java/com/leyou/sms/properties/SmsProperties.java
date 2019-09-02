package com.leyou.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: SmsPeoperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2313:04
 */
@Data
@ConfigurationProperties(prefix = "leyou.sms")
@Component
public class SmsProperties {
    private String accessKeyId;
    private String accessSecret;
    private String signName;
    private String templateCode;

}
