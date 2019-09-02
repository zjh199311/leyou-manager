package com.leyou.item.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_pay_log")
public class PayLog implements Serializable {
    /**
     * 订单号
     */
    @Id
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 支付金额（分）
     */
    @Column(name = "total_fee")
    private Long totalFee;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 微信交易号码
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 交易状态，1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭
     */
    @Column(name = "status")
    private Boolean status;

    /**
     * 支付方式，1 微信支付, 2 货到付款
     */
    @Column(name = "pay_type")
    private Boolean payType;

    /**
     * 银行类型
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 关闭时间
     */
    @Column(name = "closed_time")
    private Date closedTime;

    /**
     * 退款时间
     */
    @Column(name = "refund_time")
    private Date refundTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取订单号
     *
     * @return order_id - 订单号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号
     *
     * @param orderId 订单号
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取支付金额（分）
     *
     * @return total_fee - 支付金额（分）
     */
    public Long getTotalFee() {
        return totalFee;
    }

    /**
     * 设置支付金额（分）
     *
     * @param totalFee 支付金额（分）
     */
    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取微信交易号码
     *
     * @return transaction_id - 微信交易号码
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置微信交易号码
     *
     * @param transactionId 微信交易号码
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * 获取交易状态，1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭
     *
     * @return status - 交易状态，1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置交易状态，1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭
     *
     * @param status 交易状态，1 未支付, 2已支付, 3 已退款, 4 支付错误, 5 已关闭
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取支付方式，1 微信支付, 2 货到付款
     *
     * @return pay_type - 支付方式，1 微信支付, 2 货到付款
     */
    public Boolean getPayType() {
        return payType;
    }

    /**
     * 设置支付方式，1 微信支付, 2 货到付款
     *
     * @param payType 支付方式，1 微信支付, 2 货到付款
     */
    public void setPayType(Boolean payType) {
        this.payType = payType;
    }

    /**
     * 获取银行类型
     *
     * @return bank_type - 银行类型
     */
    public String getBankType() {
        return bankType;
    }

    /**
     * 设置银行类型
     *
     * @param bankType 银行类型
     */
    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取关闭时间
     *
     * @return closed_time - 关闭时间
     */
    public Date getClosedTime() {
        return closedTime;
    }

    /**
     * 设置关闭时间
     *
     * @param closedTime 关闭时间
     */
    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    /**
     * 获取退款时间
     *
     * @return refund_time - 退款时间
     */
    public Date getRefundTime() {
        return refundTime;
    }

    /**
     * 设置退款时间
     *
     * @param refundTime 退款时间
     */
    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }
}