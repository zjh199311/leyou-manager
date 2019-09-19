package com.leyou.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author river
 * @title: FilterPropertites
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1616:30
 */
@Component
@ConfigurationProperties(prefix = "leyou.filter")
public class FilterPropertites {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
