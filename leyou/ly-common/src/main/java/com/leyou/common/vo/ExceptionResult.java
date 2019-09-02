package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author river
 * @title: ExceptionResult
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/115:48
 */
@Data
public class ExceptionResult {

     private  int status;
     private  String message;
     private  long timestamp;

     public  ExceptionResult(ExceptionEnum exceptionEnum){
         this.status = status;
         this.message = message;
         this.timestamp = System.currentTimeMillis();
     }
}
