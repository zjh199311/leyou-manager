package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author river
 * @title: MvcConfig 让自定义过滤器生效
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1717:40
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer{
    @Autowired
    private  JwtProperties jwtProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //配置登录拦截器
        registry.addInterceptor(new LoginInterceptor(jwtProperties)).addPathPatterns("/**");
    }
}
