package com.leyou.item.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_category_brand")
public class CategoryBrand implements Serializable {
    /**
     * 商品类目id
     */
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    private Long brandId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品类目id
     *
     * @return category_id - 商品类目id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置商品类目id
     *
     * @param categoryId 商品类目id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置品牌id
     *
     * @param brandId 品牌id
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}