package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author river
 * @title: User
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2910:45
 */
@Table(name = "tb_user")
@Data
public class User {
     @Id
     @KeySql(useGeneratedKeys = true)
     private  Long id;
     @Length(min = 4,max = 30,message = "用户名的长度只能在4-30之间")
     private String username;
     @JsonIgnore
     //密码至少8个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）
     @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
     private  String password;
     @Pattern(regexp = "^1[35678]\\d{9}$",message = "手机号格式不正确")
     private  String phone;
     private Date created;
     @JsonIgnore
     private  String salt;


}
