package com.leyou.user.untils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

 /**
 　　* @description: TODO
 　　* @param ${tags}
 　　* @return ${return_type}
 　　* @throws
 　　* @author river
 　　* @date 2019/8/30 16:02
 　　*/
public class CodecUtils {

    public static String md5Hex(String data,String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    public static String generateSalt(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }


     public static void main(String[] args) {
         String s = CodecUtils.md5Hex("123456", "05b0f203987e49d2b72b20b95e0e57d9");
         System.out.println(s);
     }
}
