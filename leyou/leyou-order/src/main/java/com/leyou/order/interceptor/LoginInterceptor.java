package com.leyou.order.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.order.config.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author river
 * @title: LoginInterceptor 对请求做前置拦截，判断用户状态
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1716:33
 */
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    public LoginInterceptor(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }

    public LoginInterceptor(){
        super();
    }

    //定义一个线程域，存放登录的对象
    private static final ThreadLocal<UserInfo> t1 = new ThreadLocal<>();

    @Override
    //前置处理
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //查询token
        String login_token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        //用户未登录,返回401，拦截
        if (StringUtils.isBlank(login_token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            //用户登入，解析login_token
            UserInfo userInfo = JwtUtils.getUserInfo(jwtProperties.getPublicKey(), login_token);
            //将用户放入线程域中
            t1.set(userInfo);
            return true;
        } catch (Exception e) {
            //抛出异常，未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }


    @Override
    //视图渲染完毕后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //过滤器完成后，从线程域中删除用户信息
        t1.remove();
    }


    //将用户放入线程域，取出用户
    public  static  UserInfo  getUserInfo(){
        UserInfo userInfo = t1.get();
        return  userInfo;
    }
}
