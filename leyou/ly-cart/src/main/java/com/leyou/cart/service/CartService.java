package com.leyou.cart.service;

import com.leyou.cart.entity.Cart;

import java.util.List;

/**
 * @author river
 * @title: CartService
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1813:34
 */
public interface CartService {

    //添加购物车
    void addCart(Cart cart);
    //查询购物车，根据用户id查询购物车
    List<Cart> queryListCart();
}
