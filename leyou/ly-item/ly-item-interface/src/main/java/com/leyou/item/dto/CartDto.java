package com.leyou.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author river
 * @title: CartDto
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2113:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    //商品的id
    private Long sku_id;
    //购买商品的数量
    private Integer num;
}
