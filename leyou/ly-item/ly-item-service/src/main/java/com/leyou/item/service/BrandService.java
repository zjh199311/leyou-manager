package com.leyou.item.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @author river
 * @title: BrandService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/611:25
 */
public interface BrandService {

    //分页查询品牌信息
    PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key);
   //保存品牌信息
    void saveBrand(Brand brand, List<Long> cids);
    //根据id查询品牌信息
    Brand queryById(Long id);
    //根据分类id查询品牌
    List<Brand> queryBrandByCategoryId(Long cid);

    List<Brand> queryBrandByIds(List<Long> ids);
}
