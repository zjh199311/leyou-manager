package com.leyou.item.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_brand")
public class Brand implements Serializable {
    /**
     * 品牌id
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;

    /**
     * 品牌名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 品牌图片地址
     */
    @Column(name = "image")
    private String image;

    /**
     * 品牌的首字母
     */
    @Column(name = "letter")
    private String letter;

    private static final long serialVersionUID = 1L;

    /**
     * 获取品牌id
     *
     * @return id - 品牌id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置品牌id
     *
     * @param id 品牌id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取品牌名称
     *
     * @return name - 品牌名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置品牌名称
     *
     * @param name 品牌名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取品牌图片地址
     *
     * @return image - 品牌图片地址
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置品牌图片地址
     *
     * @param image 品牌图片地址
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取品牌的首字母
     *
     * @return letter - 品牌的首字母
     */
    public String getLetter() {
        return letter;
    }

    /**
     * 设置品牌的首字母
     *
     * @param letter 品牌的首字母
     */
    public void setLetter(String letter) {
        this.letter = letter == null ? null : letter.trim();
    }
}