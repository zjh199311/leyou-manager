package com.leyou.auth;

import com.leyou.LyAuthApplication;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.service.imply.AuthServiceImply;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * @author river
 * @title: Test
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/910:45
 */
@SpringBootTest(classes = LyAuthApplication.class)
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private AuthService authService;

   @org.junit.Test
     public void testCompent(){
        authService.login("zhangsan","123456");
     }
}
