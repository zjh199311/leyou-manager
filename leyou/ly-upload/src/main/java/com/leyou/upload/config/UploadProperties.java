package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author river
 * @title: UploadProperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/614:34
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
@Component
public class UploadProperties {
    private  String baseUrl;
    private List<String> allowTypes;
}
