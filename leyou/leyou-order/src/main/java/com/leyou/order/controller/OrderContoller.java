package com.leyou.order.controller;

import com.leyou.item.dto.CartDto;
import com.leyou.order.dto.OrderDto;
import com.leyou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author river
 * @title: OrderContoller
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2113:32
 */
@RestController
@RequestMapping("/order")
public class OrderContoller {

    @Autowired
    private OrderService orderService;


    /**
     * 　　* @description: 创建订单
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/9/21 13:36
     *
     */
    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(
            @RequestBody  OrderDto orderDto
    ) {
        String orderId = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);

    }
}
