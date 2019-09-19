package com.leyou.cart.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author river
 * @title: JwtProperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/713:47
 */
@ConfigurationProperties(prefix = "leyou.cart")
@Slf4j
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {
    private String pubKeyPath; //公钥地址
    private PublicKey publicKey; // 公钥
    private  String cookieName;  //cookie名称

    //对象一旦实例化，读取公钥和私钥
    @PostConstruct //对象实例化后执行
    public void  init(){
        try {
            //读取公钥
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥私钥失败",e);
            e.printStackTrace();
        }
    }
}
