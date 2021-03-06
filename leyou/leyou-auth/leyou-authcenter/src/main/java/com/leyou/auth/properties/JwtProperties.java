package com.leyou.auth.properties;

import com.leyou.auth.utils.RsaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author river
 * @title: JwtProperties
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/713:47
 */
@ConfigurationProperties(prefix = "leyou.jwt")
@Slf4j
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {
    private String secret;  //登录校验的密钥
    private String pubKeyPath; //公钥地址
    private String priKeyPath;//私钥地址
    private int expire;  //token的失效时间
    private PublicKey publicKey; //读取到的公钥
    private PrivateKey privateKey; //读取到的私钥
    private  String cookieName;  //cookie
    private int cookieMaxAge; //cookie的保存时间

    //对象一旦实例化，读取公钥和私钥
    @PostConstruct //对象实例化后执行
    public void  init(){
        //首先判断公钥私钥是否存在，不存在则先生成公钥私钥
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()){
                //创建公钥,私钥
                RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
            }
            //公钥、私钥都存在,读取公钥和私钥
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥私钥失败",e);
            e.printStackTrace();
        }
    }
}
