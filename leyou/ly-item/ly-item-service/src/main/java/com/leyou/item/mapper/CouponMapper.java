package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.Coupon;
import com.leyou.item.pojo.CouponExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CouponMapper extends Mapper<Coupon> {
    int countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    List<Coupon> selectByExample(CouponExample example);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);
}