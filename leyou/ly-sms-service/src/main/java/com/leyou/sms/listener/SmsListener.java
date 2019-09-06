package com.leyou.sms.listener;

import com.leyou.sms.properties.SmsProperties;
import com.leyou.sms.until.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author river
 * @title: SmsListener
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2611:23
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {
    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private SmsUtil smsUtil;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "ly.sms.verify.queue"),
            exchange = @Exchange(name = "ly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))

    public void listenVerifyCode(Map<String, Object> msg) {
        if (msg == null) {
            return;
        }
        String phoneNumbers = (String) msg.get("phoneNumbers");//remove 删除对象取值
        if (StringUtils.isBlank(phoneNumbers)) {
            return;
        }
        smsUtil.sendSms(smsProperties.getSignName(),smsProperties.getTemplateCode(), phoneNumbers, msg);
        //发送日志
        log.info("[短信服务]，发送短信验证码，手机号：{}",phoneNumbers);
    }
}
