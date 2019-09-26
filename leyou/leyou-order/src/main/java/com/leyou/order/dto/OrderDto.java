package com.leyou.order.dto;

import com.leyou.item.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author river
 * @title: OrderDto
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2113:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    //收货人地址id
    private  Long addressId;
    //支付方式
    private  Integer payment_type;
    //送货清单
    private List<CartDto> cartDtoList;
}
