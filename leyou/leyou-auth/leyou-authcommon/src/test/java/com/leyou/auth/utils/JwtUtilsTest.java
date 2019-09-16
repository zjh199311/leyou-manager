package com.leyou.auth.utils;

import com.leyou.auth.entity.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author river
 * @title: JwtUtilsTest
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/710:23
 */
public class JwtUtilsTest {
    //公钥存储路径
    private  static  final  String pubKeyPath = "D:\\Git\\rsa\\rsa.pub";
    //私钥存储路径
    private  static  final  String priKeyPath = "D:\\Git\\rsa\\rsa.pri";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    //测试生成公钥和私钥
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        privateKey = RsaUtils.getPrivateKey(priKeyPath);
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }

    //测试生成toke
    @Test
    public  void  createToken(){
        String token = JwtUtils.generateToken(new UserInfo(20, "jack"), privateKey, 5);
        System.out.println(token);
    }

    //测试解析token
    @Test
    public void  parseToken(){
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1" +
                "lIjoiamFjayIsImV4cCI6MTU2NzgyNTYzOH0.NlnlPNh20r8cys" +
                "66o5zglT0nr-wTJjVgDY9S3ggY2Qow57HgJZ62fOPYmkZ5ilxTE57" +
                "uh9c6wQl-DvKVcWJHg3imMq3n4NFJ4Sc-ZfSazoSafbdLwB2QrqqOA" +
                "gJF265If42iRimkk2B2CuxebleP2sum4YYh3lVCGzT3dnZqfxk";
        UserInfo userInfo = JwtUtils.getUserInfo(publicKey, token);
        System.out.println("id"+ userInfo.getId());
        System.out.println("useInfoName"+ userInfo.getName());

    }
}
