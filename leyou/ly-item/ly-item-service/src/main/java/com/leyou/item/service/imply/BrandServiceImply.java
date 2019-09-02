package com.leyou.item.service.imply;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author river
 * @title: BrandServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/611:26
 */
@Service
public class BrandServiceImply implements BrandService{
    @Autowired
    private BrandMapper brandMapper ;
    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        PageHelper.startPage(page,rows);
        //数据过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)){
             example.createCriteria().andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? "ASC" : "DESC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        Page<Brand> pageInfo = (Page<Brand>)brands;
        return  new PageResult<>(pageInfo.getTotal(),pageInfo);

    }

    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌信息
        brand.setId(null);
        int i= this.brandMapper.insert(brand);
        if (i!=1){
            throw  new LyException(ExceptionEnum.BRAND_CREATE_FAILED);
        }
        //新增品牌中间表
        for (Long cid : cids) {
            int count = this.brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count!=1){
                throw  new LyException(ExceptionEnum.BRAND_CREATE_FAILED);
            }
        }
    }

    @Override
    public Brand queryById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand==null){
            throw new  LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }

    @Override
    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Brand> brandList = brandMapper.queryByCategoryId(cid);
        if (CollectionUtils.isEmpty(brandList)){
            throw  new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brandList;
    }

    @Override
    public List<Brand> queryBrandByIds(List<Long> ids) {
        final List<Brand> brandList = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brandList;
    }
}
