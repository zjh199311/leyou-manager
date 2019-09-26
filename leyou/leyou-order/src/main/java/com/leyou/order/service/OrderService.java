package com.leyou.order.service;

import com.leyou.order.dto.OrderDto; /**
 * @author river
 * @title: OrderService
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2113:33
 */
public interface OrderService {
    //创建订单
    String createOrder(OrderDto orderDto);
}
