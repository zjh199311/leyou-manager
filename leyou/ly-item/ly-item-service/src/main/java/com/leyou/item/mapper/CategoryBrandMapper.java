package com.leyou.item.mapper;


import java.util.List;

import com.leyou.common.mapper.BaseMappers;
import com.leyou.item.pojo.CategoryBrandExample;
import com.leyou.item.pojo.CategoryBrand;
import org.apache.ibatis.annotations.Param;

public interface CategoryBrandMapper extends BaseMappers<CategoryBrand,Long> {
    int countByExample(CategoryBrandExample example);

    int deleteByExample(CategoryBrandExample example);

    List<CategoryBrand> selectByExample(CategoryBrandExample example);

    int updateByExampleSelective(@Param("record") CategoryBrand record, @Param("example") CategoryBrandExample example);

    int updateByExample(@Param("record") CategoryBrand record, @Param("example") CategoryBrandExample example);
}