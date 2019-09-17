package com.leyou.auth.controller;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author river
 * @title: AuthController
 * @projectName leyou
 * @description:用户认证模块
 * @date 2019/9/715:00
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties jwtProperties;

     /**
     　　* @description: 生成token并将token写入cookie中
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/16 15:03
     　　*/
    @PostMapping("/accredit")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            HttpServletRequest request
    ){
        String login_token = authService.login(username, password);
        //将login_toke写入cookie中
        CookieUtils.newBuilder(response).httpOnly().maxAge(jwtProperties.getCookieMaxAge()).request(request).build(jwtProperties.getCookieName(), login_token);
        return ResponseEntity.ok().build();
    }

     /**
     　　* @description: 校验用户信息
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/16 15:02
     　　*/
     @GetMapping("/verify")
     public ResponseEntity<UserInfo> verifUser(
             @CookieValue(value = "LY_TOKEN")String login_token,
             HttpServletRequest request,
             HttpServletResponse response
     ){
         try {
             //解析login_token
             UserInfo userInfo = JwtUtils.getUserInfo(jwtProperties.getPublicKey(), login_token);
             //解析成功，更新login_token到cookie中
             String new_login_token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
             CookieUtils.newBuilder(response).httpOnly().maxAge(jwtProperties.getCookieMaxAge()).request(request).build(jwtProperties.getCookieName(), new_login_token);
             return  ResponseEntity.ok(userInfo);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
}
