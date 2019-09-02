package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SpuMapper extends Mapper<Spu> {
    int countByExample(SpuExample example);

    int deleteByExample(SpuExample example);

    List<Spu> selectByExample(SpuExample example);

    int updateByExampleSelective(@Param("record") Spu record, @Param("example") SpuExample example);

    int updateByExample(@Param("record") Spu record, @Param("example") SpuExample example);
}