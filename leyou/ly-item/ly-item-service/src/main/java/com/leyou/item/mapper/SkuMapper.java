package com.leyou.item.mapper;


import java.util.List;

import com.leyou.common.mapper.BaseMappers;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SkuExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends BaseMappers<Sku,Long> {
    int countByExample(SkuExample example);

    int deleteByExample(SkuExample example);

    List<Sku> selectByExample(SkuExample example);

    int updateByExampleSelective(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByExample(@Param("record") Sku record, @Param("example") SkuExample example);
}