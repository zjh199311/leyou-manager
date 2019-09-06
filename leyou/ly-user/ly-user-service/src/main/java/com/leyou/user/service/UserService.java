package com.leyou.user.service;

import com.leyou.user.pojo.User; /**
 * @author river
 * @title: UserService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2913:33
 */
public interface UserService {

    //根据数据校验是否可用接口
    Boolean checkData(String data, Integer type);

    //发送验证码
    void  sendVerifyCode(String phone);

    //用户注册功能接口
    void register(User user, String code);

    User queryUserNameAndPassword(String username, String password);
}
