package com.leyou.item.mapper;

import java.util.List;

import com.leyou.item.pojo.OrderStatus;
import com.leyou.item.pojo.OrderStatusExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrderStatusMapper extends Mapper<OrderStatus> {
    int countByExample(OrderStatusExample example);

    int deleteByExample(OrderStatusExample example);

    List<OrderStatus> selectByExample(OrderStatusExample example);

    int updateByExampleSelective(@Param("record") OrderStatus record, @Param("example") OrderStatusExample example);

    int updateByExample(@Param("record") OrderStatus record, @Param("example") OrderStatusExample example);
}