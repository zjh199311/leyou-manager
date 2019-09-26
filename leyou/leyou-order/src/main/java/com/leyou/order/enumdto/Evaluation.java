package com.leyou.order.enumdto;

import lombok.AllArgsConstructor;

/**
 * @author river
 * @title: Evaluation
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2114:50
 */
@AllArgsConstructor
public enum Evaluation {
    NO_COMMENT(0,"未评价"),
    REVIEWED(1,"已评价")
    ;
    private Integer code;
    private  String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
