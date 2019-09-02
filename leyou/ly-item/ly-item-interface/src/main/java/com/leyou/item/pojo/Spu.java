package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.annotation.KeySql;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "tb_spu")
public class Spu implements Serializable {
    /**
     * spu id
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 子标题
     */
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 1级类目id
     */
    @Column(name = "cid1")
    private Long cid1;

    /**
     * 2级类目id
     */
    @Column(name = "cid2")
    private Long cid2;

    /**
     * 3级类目id
     */
    @Column(name = "cid3")
    private Long cid3;

    /**
     * 商品所属品牌id
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 是否上架，0下架，1上架
     */
    @Column(name = "saleable")
    private Boolean saleable;

    /**
     * 是否有效，0已删除，1有效
     */
    @Column(name = "valid")
    private Boolean valid;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */

    @Column(name = "last_update_time")
    @JsonIgnore
    private Date lastUpdateTime;

    @Transient
    //品牌的名称
    private  String bname;
    @Transient
    //分类的名称
    private  String  cname;

    @Transient
    private List<Sku> skus;

    @Transient
    private  SpuDetail spuDetail;


    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取spu id
     *
     * @return id - spu id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置spu id
     *
     * @param id spu id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取子标题
     *
     * @return sub_title - 子标题
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * 设置子标题
     *
     * @param subTitle 子标题
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    /**
     * 获取1级类目id
     *
     * @return cid1 - 1级类目id
     */
    public Long getCid1() {
        return cid1;
    }

    /**
     * 设置1级类目id
     *
     * @param cid1 1级类目id
     */
    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    /**
     * 获取2级类目id
     *
     * @return cid2 - 2级类目id
     */
    public Long getCid2() {
        return cid2;
    }

    /**
     * 设置2级类目id
     *
     * @param cid2 2级类目id
     */
    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    /**
     * 获取3级类目id
     *
     * @return cid3 - 3级类目id
     */
    public Long getCid3() {
        return cid3;
    }

    /**
     * 设置3级类目id
     *
     * @param cid3 3级类目id
     */
    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    /**
     * 获取商品所属品牌id
     *
     * @return brand_id - 商品所属品牌id
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置商品所属品牌id
     *
     * @param brandId 商品所属品牌id
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取是否上架，0下架，1上架
     *
     * @return saleable - 是否上架，0下架，1上架
     */
    public Boolean getSaleable() {
        return saleable;
    }

    /**
     * 设置是否上架，0下架，1上架
     *
     * @param saleable 是否上架，0下架，1上架
     */
    public void setSaleable(Boolean saleable) {
        this.saleable = saleable;
    }

    /**
     * 获取是否有效，0已删除，1有效
     *
     * @return valid - 是否有效，0已删除，1有效
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置是否有效，0已删除，1有效
     *
     * @param valid 是否有效，0已删除，1有效
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * 获取添加时间
     *
     * @return create_time - 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加时间
     *
     * @param createTime 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后修改时间
     *
     * @return last_update_time - 最后修改时间
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param lastUpdateTime 最后修改时间
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}