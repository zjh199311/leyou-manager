package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author river
 * @title: BrandApi
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:01
 */
public interface BrandApi {

    @GetMapping("/brand/{id}")
    Brand queryById(@PathVariable("id")Long id);

    @GetMapping("/brand/list")
    List<Brand> queryBrandsByIds(@RequestParam("ids") List<Long> ids);
}
