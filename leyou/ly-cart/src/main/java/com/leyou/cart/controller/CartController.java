package com.leyou.cart.controller;

import com.leyou.cart.entity.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author river
 * @title: CartController
 * @projectName leyou
 * @description:
 * @date 2019/9/1813:34
 */

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

     /**
     　　* @description: 添加购物车 Map<用户id，Map<商品id，购物车的内容>>
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/18 13:38
     　　*/
    @PostMapping("/addcart")
    public ResponseEntity<Void> addCart(
           @RequestParam("userId") Long userId,
           @RequestParam("skuId") Long skuId,
           @RequestParam("title") String title,
           @RequestParam("image") String image,
           @RequestParam("price") Long price,
           @RequestParam("num") Integer num,
           @RequestParam("ownSpec") String ownSpec

    ){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setSkuId(skuId);
        cart.setTitle(title);
        cart.setImage(image);
        cart.setPrice(price);
        cart.setNum(num);
        cart.setOwnSpec(ownSpec);
        cartService.addCart(cart);
        return  ResponseEntity.ok().build();
    }


     /**
     　　* @description: 查询购物车，根据用户id查询购物车
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/18 15:05
     　　*/
    @GetMapping("/querycart")
    public ResponseEntity<List<Cart>> queryListCart(){
            List<Cart> cartList = cartService.queryListCart();
            if (CollectionUtils.isEmpty(cartList)){
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return  ResponseEntity.ok(cartList);
    }

     /**
     　　* @description: 修改购物车数量
     　　* @param  shopId 商品id
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/9/20 9:33
     　　*/
    @PostMapping("/updateCart")
    public  ResponseEntity<Void> updateNum(@RequestParam("shopId") Long shopId, @RequestParam("num") Integer num){
           cartService.updateNum(shopId,num);
           return  ResponseEntity.ok().build();
    }


    /**
     * 从购物车中删除商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok().build();
    }

}
