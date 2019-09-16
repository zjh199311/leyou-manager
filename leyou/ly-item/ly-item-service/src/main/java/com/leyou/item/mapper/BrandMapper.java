package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMappers;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.*;


import java.util.List;

/**
 * @author bystander
 * @date 2018/9/16
 */
public interface BrandMapper extends BaseMappers<Brand,Long> {

    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{cid}, #{bid})")
    int saveCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> queryCategoryByBid(Long bid);

    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    int deleteCategoryBrandByBid(Long bid);

    @Select("select * from tb_brand where id in (select brand_id from tb_category_brand where category_id = #{cid})")
    List<Brand> queryBrandByCid(Long cid);


    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param id 品牌id
     * @return
     */
    @Insert("INSERT INTO  tb_category_brand (category_id,brand_id) VALUES (#{cid} ,#{id})")
    int insertCategoryBrand(@Param("cid")Long cid,@Param("id")Long id);

     /**
     　　* @description: 根据分类id查询品牌(中间表)
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/8/8 15:29
     　　*/
     @Select("SELECT  b.*  FROM  tb_brand b  LEFT  JOIN  tb_category_brand tcb ON b.id = tcb.brand_id WHERE  tcb.category_id = #{cid} ")
     List<Brand> queryByCategoryId(Long cid);

}
