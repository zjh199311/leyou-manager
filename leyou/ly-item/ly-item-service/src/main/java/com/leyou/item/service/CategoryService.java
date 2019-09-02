package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author river
 * @title: CategoryService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/210:14
 */
public interface CategoryService {
    List<Category> queryCategoryByPid(Long pid);

    List<Category> queryCategoryByIds(List<Long> ids);

    List<Category> queryAllByCid3(Long id);
}
