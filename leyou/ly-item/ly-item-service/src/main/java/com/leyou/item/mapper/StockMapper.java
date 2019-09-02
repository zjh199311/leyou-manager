package com.leyou.item.mapper;


import java.util.List;

import com.leyou.common.mapper.BaseMappers;
import com.leyou.item.pojo.Stock;
import com.leyou.item.pojo.StockExample;
import org.apache.ibatis.annotations.Param;


public interface StockMapper extends BaseMappers<Stock,Long> {
    int countByExample(StockExample example);

    int deleteByExample(StockExample example);

    List<Stock> selectByExample(StockExample example);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);
}