package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.SpuDetailExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SpuDetailMapper extends Mapper<SpuDetail> {
    int countByExample(SpuDetailExample example);

    int deleteByExample(SpuDetailExample example);

    List<SpuDetail> selectByExampleWithBLOBs(SpuDetailExample example);

    List<SpuDetail> selectByExample(SpuDetailExample example);

    int updateByExampleSelective(@Param("record") SpuDetail record, @Param("example") SpuDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") SpuDetail record, @Param("example") SpuDetailExample example);

    int updateByExample(@Param("record") SpuDetail record, @Param("example") SpuDetailExample example);
}