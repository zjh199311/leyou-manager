package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @author river
 * @title: LyException
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/115:39
 */
@Getter
public class LyException extends RuntimeException {
     private ExceptionEnum exceptionEnum;
     public  LyException (ExceptionEnum exceptionEnum){
         this.exceptionEnum = exceptionEnum;
     }
}
