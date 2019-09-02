package com.leyou.search.client;

import com.leyou.item.pojo.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author river
 * @title: BrandClientTest
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandClientTest {
     @Autowired
     private  BrandClient brandClient;
     @Test
     public  void  queryById(){
          Brand brand = brandClient.queryById(1528L);
         System.out.println(brand.toString());
     }
}