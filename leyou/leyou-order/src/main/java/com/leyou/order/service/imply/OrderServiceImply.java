package com.leyou.order.service.imply;

import com.leyou.auth.entity.UserInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.IdWorker;
import com.leyou.item.pojo.Sku;
import com.leyou.order.client.AddressClient;
import com.leyou.order.client.GoodsClient;
import com.leyou.order.dto.AddressDTO;
import com.leyou.order.dto.OrderDto;
import com.leyou.order.enumdto.PayStatus;
import com.leyou.order.enumdto.PaymentType;
import com.leyou.order.interceptor.LoginInterceptor;
import com.leyou.order.mapper.OrderDetailMapper;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStausMapper;
import com.leyou.order.projo.Order;
import com.leyou.order.projo.OrderDetail;
import com.leyou.order.projo.OrderStatus;
import com.leyou.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author river
 * @title: OrderServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2113:33
 */
@Service
public class OrderServiceImply implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderStausMapper orderStausMapper;
    @Autowired
    private IdWorker idWorker;
    @Resource
    private GoodsClient goodsClient;
    @Override
    @Transactional
    public String createOrder(OrderDto orderDto) {
        //1.新增订单
        String orderId = idWorker.nextId();
        //1.1 订单编号的其他基本信息
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setPaymentType(PaymentType.ONLINE_PAYMENT.getCode());
        order.setPostFee(0L);
        //1.2 用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        order.setUserId(Integer.toString(userInfo.getId()));
        order.setBuyerNick(userInfo.getName());
        order.setBuyerMessage("");
        // 1.3 收货人地址
        AddressDTO addressDTO = AddressClient.findById(orderDto.getAddressId());
        if (addressDTO==null){
             throw  new LyException(ExceptionEnum.RECEIVER_ADDRESS_NOT_FOUND);
        }
        order.setReceiver(addressDTO.getName());
        order.setReceiverAddress(addressDTO.getAddress());
        order.setReceiverCity(addressDTO.getCity());
        order.setReceiverDistrict(addressDTO.getDistrict());
        order.setReceiverMobile(addressDTO.getPhone());
        order.setReceiverZip(addressDTO.getZipCode());
        order.setReceiverState(addressDTO.getState());
        //1.4 金额
        //key是商品的id，值是商品的数量
        Map<Long, Integer> skuNumMap = orderDto.getCartDtoList().stream().
                        collect(Collectors.toMap(c -> c.getSku_id(), c -> c.getNum()));
        List<Long> idList = new ArrayList<>(skuNumMap.keySet());
        //根据skuIds批量查询sku详情
        List<Sku> skus = goodsClient.querySkusByIds(idList);
        if (CollectionUtils.isEmpty(skus)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        List<OrderDetail> orderDetailList =  new ArrayList<>();
        Long totalPrice = 0L;
        for (Sku sku : skus) {
            Integer num = skuNumMap.get(sku.getId());
            totalPrice= num * sku.getPrice();
            //填充orderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNum(num);
            orderDetail.setImage(StringUtils.substringBefore(sku.getImages(),","));
            orderDetail.setTitle(sku.getTitle());
            orderDetail.setPrice(sku.getPrice());
            orderDetail.setOwnSpec(sku.getOwnSpec());
            orderDetail.setSkuId(sku.getId());
            //2.订单详情
            orderDetailList.add(orderDetail);
        }
        //用户实付金额总价+邮费-优惠金额
        order.setActualPay(totalPrice.longValue()+order.getPostFee());
        order.setTotalPay(totalPrice.longValue());
        //保存order
        int orderInt = orderMapper.insertSelective(order);
        if (orderInt!=1){
            throw new LyException(ExceptionEnum.INSERT_ORDER_ERR);
        }
        //保存orderdetail
        int orderDetailInt = orderDetailMapper.insertList(orderDetailList);
        if (orderDetailInt!=1){
            throw new LyException(ExceptionEnum.INSERT_ORDERDETAIL_ERR);
        }
        //3.订单的状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(PayStatus.NO_PAY.getCode());
        orderStatus.setCreateTime(new Date());
        //保存OrderStaus
        int orderStatusInt = orderStausMapper.insertSelective(orderStatus);
        if (orderStatusInt!=1){
            throw new LyException(ExceptionEnum.INSERT_ORDERSTATUS_ERR);
        }
        //4.库存的变更
        //减库存
        goodsClient.decreaseStock(orderDto.getCartDtoList());
        return orderId;
    }
}
