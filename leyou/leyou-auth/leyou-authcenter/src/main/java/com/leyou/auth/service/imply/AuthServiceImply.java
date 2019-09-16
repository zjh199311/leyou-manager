package com.leyou.auth.service.imply;


import com.leyou.auth.client.UserClient;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author river
 * @title: AuthServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/715:05
 */

@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
@Service
public class AuthServiceImply implements AuthService{
    @Resource
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public String login(String username, String password) {
        try {
            User user = userClient.queryUserNameAndPassword(username, password);
            if (user==null){
                throw  new LyException(ExceptionEnum.USER_NOT_EXIST);
            }
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
            //生成login_token
            String longin_token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return  longin_token;
        } catch (LyException e) {
            log.error("【授权中心】用户名和密码错误，用户名：{}", username,e);
            throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }
}
