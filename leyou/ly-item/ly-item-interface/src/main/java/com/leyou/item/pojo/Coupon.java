package com.leyou.item.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_coupon")
public class Coupon implements Serializable {
    /**
     * 优惠卷id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 优惠卷名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 优惠卷类型,1、抵扣  2、折扣(打折）
     */
    @Column(name = "type")
    private String type;

    /**
     * 抵扣或折扣条件，如果没有限制，则设置为0
     */
    @Column(name = "condition")
    private Long condition;

    /**
     * 优惠金额
     */
    @Column(name = "reduction")
    private Long reduction;

    /**
     * 如果没有折扣，为100。如果是八五折，折扣为85
     */
    @Column(name = "discount")
    private Integer discount;

    /**
     * 优惠券可以生效的sku的id拼接，以,分割
     */
    @Column(name = "targets")
    private String targets;

    /**
     * 剩余优惠券数量
     */
    @Column(name = "stock")
    private Integer stock;

    /**
     * 优惠券生效时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 优惠券失效时间
     */
    @Column(name = "end_time")
    private Date endTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取优惠卷id
     *
     * @return id - 优惠卷id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置优惠卷id
     *
     * @param id 优惠卷id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取优惠卷名称
     *
     * @return name - 优惠卷名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置优惠卷名称
     *
     * @param name 优惠卷名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取优惠卷类型,1、抵扣  2、折扣(打折）
     *
     * @return type - 优惠卷类型,1、抵扣  2、折扣(打折）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置优惠卷类型,1、抵扣  2、折扣(打折）
     *
     * @param type 优惠卷类型,1、抵扣  2、折扣(打折）
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取抵扣或折扣条件，如果没有限制，则设置为0
     *
     * @return condition - 抵扣或折扣条件，如果没有限制，则设置为0
     */
    public Long getCondition() {
        return condition;
    }

    /**
     * 设置抵扣或折扣条件，如果没有限制，则设置为0
     *
     * @param condition 抵扣或折扣条件，如果没有限制，则设置为0
     */
    public void setCondition(Long condition) {
        this.condition = condition;
    }

    /**
     * 获取优惠金额
     *
     * @return reduction - 优惠金额
     */
    public Long getReduction() {
        return reduction;
    }

    /**
     * 设置优惠金额
     *
     * @param reduction 优惠金额
     */
    public void setReduction(Long reduction) {
        this.reduction = reduction;
    }

    /**
     * 获取如果没有折扣，为100。如果是八五折，折扣为85
     *
     * @return discount - 如果没有折扣，为100。如果是八五折，折扣为85
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * 设置如果没有折扣，为100。如果是八五折，折扣为85
     *
     * @param discount 如果没有折扣，为100。如果是八五折，折扣为85
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * 获取优惠券可以生效的sku的id拼接，以,分割
     *
     * @return targets - 优惠券可以生效的sku的id拼接，以,分割
     */
    public String getTargets() {
        return targets;
    }

    /**
     * 设置优惠券可以生效的sku的id拼接，以,分割
     *
     * @param targets 优惠券可以生效的sku的id拼接，以,分割
     */
    public void setTargets(String targets) {
        this.targets = targets == null ? null : targets.trim();
    }

    /**
     * 获取剩余优惠券数量
     *
     * @return stock - 剩余优惠券数量
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置剩余优惠券数量
     *
     * @param stock 剩余优惠券数量
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取优惠券生效时间
     *
     * @return start_time - 优惠券生效时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置优惠券生效时间
     *
     * @param startTime 优惠券生效时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取优惠券失效时间
     *
     * @return end_time - 优惠券失效时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置优惠券失效时间
     *
     * @param endTime 优惠券失效时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}