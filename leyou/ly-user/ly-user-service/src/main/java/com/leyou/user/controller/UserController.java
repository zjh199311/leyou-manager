package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @author river
     * @title: UserController
     * @projectName leyou
     * @description: 根据数据校验是否可用
     * data 要校验的数据  type 要校验的数据类型：1，用户名；2，手机
     * @date 2019/8/2911:15
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(
            @PathVariable("data") String data,
            @PathVariable(value = "type") Integer type) {
        Boolean checkData = userService.checkData(data, type);
        if (checkData == null){
              return  ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return  ResponseEntity.ok(checkData);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("send")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone) {
        userService.sendVerifyCode(phone);
        return ResponseEntity.noContent().build();
    }


     /**
     　　* @description: 用户注册功能(BindingResult对验证注解@Valid多余的返回信息进行过滤)
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/8/30 14:34
     　　*/
    @PostMapping("/register")
    public  ResponseEntity<Void> register(@Valid User user, BindingResult result, @RequestParam(value = "code") String code){
          if(result.hasErrors()){
              throw  new RuntimeException(result.getFieldErrors().stream()
                      .map(e -> e.getDefaultMessage()).collect(Collectors.joining("|")));
          }
          userService.register(user,code);
          return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
