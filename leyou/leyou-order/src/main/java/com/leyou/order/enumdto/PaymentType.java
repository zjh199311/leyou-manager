package com.leyou.order.enumdto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author river
 * @title: PaymentType
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2114:44
 */
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentType {
    ONLINE_PAYMENT(1,"在线支付"),//在线支付
    CASH_ON_DELIVERY(2,"货到付款"),//货到付款
    ;

    private  Integer code;
    private  String type;

    public Integer getCode() {
        return code;
    }
    public String getType() {
        return type;
    }
}
