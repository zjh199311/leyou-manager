package com.leyou.item.mapper;


import java.util.List;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecGroupExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SpecGroupMapper extends Mapper<SpecGroup> {
    int countByExample(SpecGroupExample example);

    int deleteByExample(SpecGroupExample example);

    List<SpecGroup> selectByExample(SpecGroupExample example);

    int updateByExampleSelective(@Param("record") SpecGroup record, @Param("example") SpecGroupExample example);

    int updateByExample(@Param("record") SpecGroup record, @Param("example") SpecGroupExample example);
}