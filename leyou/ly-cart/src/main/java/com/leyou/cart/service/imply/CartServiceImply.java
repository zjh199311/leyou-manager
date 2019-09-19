package com.leyou.cart.service.imply;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.entity.Cart;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.service.CartService;
import com.leyou.common.utils.JsonUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author river
 * @title: CartServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/1813:34
 */
@Service
public class CartServiceImply implements CartService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String USER_ID_PREFIX = "cart_user_id";

    @Override
    public void addCart(Cart cart) {
        //获取用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        int userId = userInfo.getId();
        //用户id
        String keyUserId = USER_ID_PREFIX + userId;
        //用户id下对应的购物车id
        String cartId = cart.getSkuId().toString();
        //判断用户是否有添加购物车
        BoundHashOperations<String, Object, Object> boundHashOperations = stringRedisTemplate.boundHashOps(keyUserId); // 获取hash操作的对象
        if (boundHashOperations.hasKey(cartId)) {
            //是，直接修改数量
            int num = cart.getNum();
            Cart cartBean = JsonUtils.toBean(boundHashOperations.get(cartId).toString(), Cart.class);
            Integer cartNum = cartBean.getNum() + num;
            cartBean.setNum(cartNum);
            //写回redis
            boundHashOperations.put(cartId,JsonUtils.toString(cartBean));

        }else {
            //否，直接添加到redis中
            boundHashOperations.put(cartId, JsonUtils.toString(cart));
        }
    }

    @Override
    public List<Cart> queryListCart() {
        //获取登入用户的信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        int userId = userInfo.getId();
        String userIdKey = USER_ID_PREFIX + userId;
        if (!stringRedisTemplate.hasKey(userIdKey)) {
            return null;
        }
        // 获取hash操作的对象
        BoundHashOperations<String, Object, Object> boundHashOperations = stringRedisTemplate.boundHashOps(userIdKey);
        List<Object> cartList = boundHashOperations.values();
        if (CollectionUtils.isEmpty(cartList)) {
            return null;
        }
        List<Cart> carts = cartList.stream().map(cart -> JsonUtils.toBean(cart.toString(), Cart.class)).collect(Collectors.toList());
        return carts;

    }
}
