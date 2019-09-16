package com.leyou.page.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author river
 * @title: PageService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1614:35
 */
@Service
@Slf4j
public class PageService {
    @Resource
    private GoodsClient goodsClient;
    @Resource
    private BrandClient brandClient;
    @Resource
    private CategoryClient categoryClient;
    @Resource
    private SpecClient specClient;
    @Value("${D:/Linux}")
    private  String dest;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String,Object> loadModel(Long spuId) {
        HashMap<String, Object> model = new HashMap<>();
        Spu spu = goodsClient.querySpuBySpuId(spuId);
        if (spu.getSaleable()==null){
            throw new LyException(ExceptionEnum.GOODS_NOT_SALEABLE);
        }
        SpuDetail detail = spu.getSpuDetail();
        List<Sku> skus = spu.getSkus();
        Brand brand = brandClient.queryById(spu.getId());
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<SpecGroup> specs = specClient.querySpecGroupByCid(spu.getCid3());
        model.put("brand",brand);
        model.put("categories",categories);
        model.put("spu",spu);
        model.put("skus",skus);
        model.put("detail",detail);
        model.put("specs",specs);
        return  null;
    }

    public  void  createHtml(Long spuId){
        // 创建thymeleaf上下文对象
        Context context = new Context();
        // 获取页面数据
        Map<String, Object> map = loadModel(spuId);
        //把数据放入到上下文对象中
        context.setVariables(map);
        File file = new File(dest, spuId + ".html");
        //如果页面存在，先删除，后进行创建静态页
        if (file.exists()){
            file.delete();
        }
        try {
            // 创建输出流
            PrintWriter printWriter = new PrintWriter(file, "utf-8");
            // 执行页面静态化方法

        } catch (Exception e) {
            log.error("【静态页服务】生成静态页面异常",e);
        }
    }

    public void deleteHtml(Long id) {
        File file = new File(this.dest + id + ".html");
        if (file.exists()) {
            boolean flag = file.delete();
            if (!flag) {
                log.error("删除静态页面失败");
            }
        }
    }
}
