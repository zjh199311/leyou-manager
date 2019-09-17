package com.leyou.user.service.imply;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.properties.YzmTimeRidesProperties;
import com.leyou.user.service.UserService;
import com.leyou.user.untils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author river
 * @title: UserService
 * @projectName leyou
 * @description: data 要校验的数据  type 要校验的数据类型：1，用户名；2，手机
 * @date 2019/8/2911:13
 */
@Slf4j
@Service
@EnableConfigurationProperties(YzmTimeRidesProperties.class)
public class UserServiceImply implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private  YzmTimeRidesProperties yzmTimeRidesProperties;
    private static final String KEY_PREFIX = "user:verify:code:";
    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1 :
                 user.setUsername(data);
                 break;
            case 2 :
                 user.setPhone(data);
                 break;
            default:
                 throw  new LyException(ExceptionEnum.INVALID_PARAM);
        }
        return  userMapper.selectOne(user)==null? false:true;

    }

    @Override
    public  void  sendVerifyCode(String phone){
        //随机生成6位数字的验证码
        String code = NumberUtils.generateCode(6);
        String key = KEY_PREFIX+phone;
        //向mq中发消息
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        //发送验证码
        amqpTemplate.convertAndSend(yzmTimeRidesProperties.getAdmqExchange(),yzmTimeRidesProperties.getAdmqKey(),map);
        //把验证码放入Redis中，并设置有效期为5min
        redisTemplate.boundValueOps(key).set(code,yzmTimeRidesProperties.getTime(), TimeUnit.MINUTES);

    }

    @Override
    public void register(User user, String code) {
        //从redis中取出验证码
        String cachCode= redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        //校验验证码
        if (!StringUtils.equals(cachCode,code)){
               throw  new LyException(ExceptionEnum.VERIFY_CODE_NOT_MATCHING);
        }
         //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //对密码进行加密
         user.setPassword(CodecUtils.md5Hex(user.getPassword(),salt));
         //写入数据库
        user.setCreated(new  Date());
        userMapper.insert(user);
    }

    @Override
    public User queryUserNameAndPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        User userObject = userMapper.selectOne(user);
        if (userObject==null){
            throw  new LyException(ExceptionEnum.USER_NOT_EXIST);
        }
        if (!StringUtils.equals(CodecUtils.md5Hex(password,userObject.getSalt()),userObject.getPassword())){
            throw  new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        return userObject;
    }

}
