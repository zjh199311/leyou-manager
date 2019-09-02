package com.leyou.search.pojo;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author river
 * @title: SearchResult
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1414:38
 */
@Data
public class SearchResult extends PageResult{
    private List<Category> categories;  //分类待选项
    private List<Brand> brands; //品牌待选项
    private List<Map<String,String>> specs; // 规格参数过滤条件
    public SearchResult(){

    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs=specs;
    }

    public SearchResult(Long total, Integer totalPage, List items, List<Category> categories, List<Brand> brands, List<Map<String, String>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
