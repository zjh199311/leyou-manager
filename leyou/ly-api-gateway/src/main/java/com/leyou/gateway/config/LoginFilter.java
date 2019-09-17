package com.leyou.gateway.config;


import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;

/**
 * @author river
 * @title: LoginFilter
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1616:58
 */
@Slf4j
@EnableConfigurationProperties({JwtProperties.class,FilterPropertites.class})
public class LoginFilter  extends ZuulFilter{
    @Autowired
    private  JwtProperties jwtProperties;
    @Autowired
    private  FilterPropertites filterPropertites;

    @Override
    //过滤器类型
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    //过滤器顺序
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    //是否过滤
    public boolean shouldFilter() {
        //获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        return !isAllowPath(requestURI);
    }

    private boolean isAllowPath(String requestURI) {
          //定义一个标记
         Boolean flage = false;
        for (String path : filterPropertites.getAllowPaths()) {
              if (requestURI.startsWith(path)){
                   flage = true;
                   break;
              }
        }
       return flage = false;
    }

    @Override
    public Object run() throws ZuulException {

            //获取上下文
            RequestContext context = RequestContext.getCurrentContext();
            HttpServletRequest request = context.getRequest();
            //获取token
            String login_token  = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        try {
            //校验通过放行
            UserInfo userInfo = JwtUtils.getUserInfo(jwtProperties.getPublicKey(), login_token);
        } catch (Exception e) {
            // 校验出现异常，返回403
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(403);
            log.error("非法访问，未登录，地址：{}", request.getRemoteHost(), e );
        }
       return  null;
    }
}
