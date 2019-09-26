package com.leyou.order.enumdto;

import lombok.AllArgsConstructor;

/**
 * @author river
 * @title: SourceType
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2115:00
 */
@AllArgsConstructor
public enum SourceType {
    APP_SIDE(1,"app端"),//app端
    PC_SIDE(2,"pc端"),//pc端
    M_SIDE(3,"M端"),//M端
    WECHAT_SIDE(4,"微信端"),//微信端
    MOBILE_QQ_SIDE(5,"手机qq端")//手机qq端
    ;
    private Integer code;
    private String source;

    public Integer getCode() {
        return code;
    }

    public String getSource() {
        return source;
    }
}
