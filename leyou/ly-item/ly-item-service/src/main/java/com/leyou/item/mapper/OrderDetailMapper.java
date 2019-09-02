package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.OrderDetail;
import com.leyou.item.pojo.OrderDetailExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrderDetailMapper extends Mapper<OrderDetail> {
    int countByExample(OrderDetailExample example);

    int deleteByExample(OrderDetailExample example);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);
}