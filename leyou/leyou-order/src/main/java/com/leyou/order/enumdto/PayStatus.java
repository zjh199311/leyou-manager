package com.leyou.order.enumdto;

import lombok.AllArgsConstructor;

/**
 * @author river
 * @title: PayStatus
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2115:05
 */
@AllArgsConstructor
public enum PayStatus {
    NO_PAY(1, "未付款"),//1、未付款
    PAID_NOT_SHIPPED(2, "已付款,未发货"),//2、已付款,未发货
    SHIPPED_UNCONFIRMED(3, "已发货,未确认"),//3、已发货,未确认
    SUCCESSFUL_TRANSACTION(4, "交易成功"),//4、交易成功
    TRADING_CLOSED(5, "交易关闭"),//5、交易关闭
    REVIEWED(6, "已评价")//6、已评价
    ;
    private Integer code;
    private String payStatus;

    public Integer getCode() {
        return code;
    }

    public String getAyStatus() {
        return payStatus;
    }
}
