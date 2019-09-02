package com.leyou.sms.until;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.properties.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author river
 * @title: SmsUtil
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2311:21
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
@Slf4j
public class SmsUtil {
    @Autowired
    private  SmsProperties smsProperties ;
    @Autowired
    private StringRedisTemplate template;
    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static final String SMS_PREFIX = "sms:phone:";
    private static final long SMS_MIN_INTERVAL_IN_MILLIS = 60000*30;

    public CommonResponse sendSms(String signName, String templateCode, String phone, Map<String,Object> templateParam	){
        try {
            String key =  SMS_PREFIX +phone;
            String lastTime = template.opsForValue().get(key);
            if (StringUtils.isNotBlank(lastTime)){
                 //Redis中键不为空
                Long time = Long.valueOf(lastTime);
                if (System.currentTimeMillis() - time < SMS_MIN_INTERVAL_IN_MILLIS){
                    //Redis中发送信息的手机号为不超过1min,则返回空，不进行短信发送
                    log.info("【短信服务】短信发送频率过高，被拦截，手机号：{}", phone);
                    return null;
                }
            }
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",smsProperties.getAccessKeyId(),smsProperties.getAccessSecret());
            DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",product,domain);
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setAction("SendSms");
            request.putQueryParameter("电话",phone);
            request.putQueryParameter("签名",signName);
            request.putQueryParameter("短信模板ID",templateCode);
            request.putQueryParameter("发送参数", JsonUtils.toString(templateParam));
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map map = (Map) JSON.parse(data);
            if ("ok".equals(map.get("Code"))){
                 //将验证码存入redis中，设置验证码失效时间
                 template.opsForValue().set(key,String.valueOf(System.currentTimeMillis()),30, TimeUnit.MINUTES);
                 return  response;
            }else {
                log.error("【消息服务】消息发送未成功，手机号码：{}，原因：{}",phone,map.get("Message"));
                return  null;
            }
        } catch (Exception e) {
            log.error("【短信服务】发送信息失败，手机号码：{}", phone,e);
            return null;
        }
    }
}
