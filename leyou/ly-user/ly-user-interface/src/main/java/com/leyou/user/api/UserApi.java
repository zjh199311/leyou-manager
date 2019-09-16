package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author river
 * @title: UserApi
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2911:08
 */
public interface UserApi {
    /**
     * @author river
     * @title: UserController
     * @projectName leyou
     * @description: 根据数据校验是否可用
     * data 要校验的数据  type 要校验的数据类型：1，用户名；2，手机
     * @date 2019/8/2911:15
     */
    @GetMapping("/user/check/{data}/{type}")
    Boolean checkUserData(@PathVariable("data") String data, @PathVariable(value = "type") Integer type);


    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("/user/send")
    Void sendVerifyCode(@RequestParam("phone")String phone);

    /**
     　　* @description: 用户注册功能(BindingResult对验证注解@Valid多余的返回信息进行过滤)
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/8/30 14:34
     　　*/
    @PostMapping("/user/register")
    void  register(@Valid User user,@RequestParam("code") String code);


    /**
     　　* @description: 根据用户名和密码查询用户
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/6 9:18
     　　*/
    @GetMapping("/user/query")
    User queryUserNameAndPassword(
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password);

}