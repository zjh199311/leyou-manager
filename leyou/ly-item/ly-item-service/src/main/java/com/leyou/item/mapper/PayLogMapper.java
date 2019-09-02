package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.PayLog;
import com.leyou.item.pojo.PayLogExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface PayLogMapper extends Mapper<PayLog> {
    int countByExample(PayLogExample example);

    int deleteByExample(PayLogExample example);

    List<PayLog> selectByExample(PayLogExample example);

    int updateByExampleSelective(@Param("record") PayLog record, @Param("example") PayLogExample example);

    int updateByExample(@Param("record") PayLog record, @Param("example") PayLogExample example);
}