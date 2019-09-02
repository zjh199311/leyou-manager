package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author river
 * @title: SearchService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1216:13
 */
@Service
@Slf4j
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private  GoodsClient goodsClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate template;
    public Goods buildGoods(Spu spu) {

        //2.查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)){
             throw  new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> categoriesNames = categories.stream().map(Category::getName).collect(Collectors.toList());
        //3.查询品牌
        Brand brand = brandClient.queryById(spu.getBrandId());
        if (brand==null){
            throw  new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //4.组成all，形成搜索字段
        String all = spu.getTitle()+ StringUtils.join(categoriesNames," ") + brand.getName();
        //5.查询sku
        List<Sku> skuList = goodsClient.querySkuBySpuId(spu.getId());
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        }
        //存储price的集合
        TreeSet<Long> priceSet = new TreeSet<>();
        List<Map<String,Object>> skus = new ArrayList<>();
        //6.对sku进行处理
        for (Sku sku : skuList) {
            priceSet.add(sku.getPrice());
            HashMap<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);
        }
        //7.查询规格参数,规格参数中分为通用规格参数和特有规格参数
        List<SpecParam> specParams = specClient.querySpecParams(null,null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(specParams)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        //8.查询商品详情
        SpuDetail spuDetail = goodsClient.querySpuDetailById(spu.getId());
        //9.获取通用规格参数
        Map<Long,String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(),Long.class,String.class);
        //10.获取特有规格参数
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });
        //11.规格参数，key是规格参数的名字，值是规格参数的值

        //定义spec对应的map
        HashMap<String,Object> map = new HashMap<>();
        for (SpecParam specParam : specParams) {
            //key是规格参数的名字
            String specParamNameKey = specParam.getName();
            Object value = "";
            if (specParam.getGeneric()){
                //参数是通用属性，通过规格参数的ID从商品详情存储的规格参数中查出值
                value= genericSpec.get(specParam.getId());
                if (specParam.getNumeric()){
                    //参数是数值类型，处理成段，方便后期对数值类型进行范围过滤
                    value=chooseSegment(value.toString(), specParam);
                }
            }else {
                //参数是特有的规格参数
                value=specialSpec.get(specParam.getId());
            }
            value=(value==null?"其他":value);
            //12.规格参数(map--key:规格参数,value:商品详情)
            //存入map中
            map.put(specParamNameKey,value);
        }

        //1.构建goods对象
        Goods goods = new Goods();
        goods.setId(spu.getId());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setAll(all);    //所有需要被搜索的信息，包括分类，品牌，标题等
        goods.setPrice(null);   //TODO 价格
        goods.setSkus(null);  // TODO  所有sku的集合的json格式
        goods.setSpecs(null); //TODO 所有的可搜索的规格参数
        goods.setSubtitle(spu.getSubTitle());
        return goods;
    }

    private  String chooseSegment(String value,SpecParam specParam){
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : specParam.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);

            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + specParam.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + specParam.getUnit() + "以下";
                } else {
                    result = segment + specParam.getUnit();
                }
                break;
            }
        }
        return result;
    }


     /**
     　　* @description: 搜索过滤
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author river
     　　* @date 2019/8/14 13:36
     　　*/
    public PageResult<Goods> search(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        if (StringUtils.isBlank(key)){
            return  null;
        }
        //构造查询条件,创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //对key进行全文检索查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));
        //通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter( new FetchSourceFilter(new String[]{"id","skus","subtitle"},null));
        //分页(准备分页参数)
        Integer page = searchRequest.getPage();
        Integer size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        //基本搜索条件
        QueryBuilder basicQuery = buildBasicQuery(searchRequest);
        queryBuilder.withQuery(basicQuery);
        //聚合分类和品牌
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        //查询获取结果
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        //解析结果
         //解析聚合结果
        Aggregations agg = result.getAggregations();
        List<Category> categories = handleCategoryAgg(agg.get(categoryAggName));
        List<Brand> brands = handleBrandAgg(agg.get(brandAggName));
        //对规格参数聚合
        List<Map<String, Object>> specs = null;

        if (categories != null && categories.size() == 1) {
            specs = handleSpecs(categories.get(0).getId(), basicQuery);
        }
        //解析分页结果
         return  new SearchResult(result.getTotalElements(),result.getTotalPages(),result.getContent(),categories,brands);

    }

    // 解析商品分类聚合结果
    public List<Category> handleCategoryAgg(LongTerms terms) {
        try {
            //获取id
            List<Long> ids = terms.getBuckets()
                    .stream()
                    .map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            //根据ID查询分类
            List<Category> categories = categoryClient.queryCategoryByIds(ids);
            for (Category category : categories) {
                category.setParentId(null);
                category.setIsParent(null);
                category.setSort(null);
            }
            return categories;
        } catch (Exception e) {
            log.error("查询分类信息失败", e);
            return null;
        }

    }

    //解析品牌聚合结果
    private List<Brand> handleBrandAgg(LongTerms terms) {
        //获取品牌ID
        try {
            List<Long> ids = terms.getBuckets()
                    .stream()
                    .map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            //根据品牌ids查询品牌
            return brandClient.queryBrandsByIds(ids);

        } catch (Exception e) {
            log.error("查询品牌信息失败", e);
            return null;
        }
    }

    //对规格参数进行聚合并解析结果
    private List<Map<String, Object>> handleSpecs(Long id, QueryBuilder basicQuery) {
        List<Map<String, Object>> specs = new ArrayList<>();

        //查询可过滤的规格参数
        List<SpecParam> params = specClient.querySpecParams(null,null,id,true);

        //基本查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);
        queryBuilder.withPageable(PageRequest.of(0, 1));

        for (SpecParam param : params) {
            //聚合
            String name = param.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }
        //查询
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        //对聚合结果进行解析
        Aggregations aggs = result.getAggregations();
        for (SpecParam param : params) {
            String name = param.getName();
            Terms terms = aggs.get(name);
            //创建聚合结果
            HashMap<String, Object> map = new HashMap<>();
            map.put("k", name);
            map.put("options", terms.getBuckets()
                    .stream()
                    .map(b -> b.getKey())
                    .collect(Collectors.toList()));
            specs.add(map);
        }
        return specs;
    }

    /**
     * 构建基本查询
     *
     * @param request
     * @return
     */
    private QueryBuilder buildBasicQuery(SearchRequest request) {
        //构建布尔查询
        BoolQueryBuilder basicQuery = QueryBuilders.boolQuery();
        //搜索条件
        basicQuery.must(QueryBuilders.matchQuery("all", request.getKey()));

        //过滤条件
        Map<String, String> filterMap = request.getFilter();

        if (!CollectionUtils.isEmpty(filterMap)) {
            for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                String key = entry.getKey();
                //判断key是否是分类或者品牌过滤条件
                if (!"cid2".equals(key) && !"brandId".equals(key)) {
                    key = "specs." + key + ".keyword";
                }
                //过滤条件
                String value = entry.getValue();
                //因为是keyword类型，使用terms查询
                basicQuery.filter(QueryBuilders.termQuery(key, value));
            }
        }
        return basicQuery;
    }


    /**
     * 插入或更新索引
     *
     * @param id
     */
    @Transactional
    public void insertOrUpdate(Long id) {
        Spu spu = goodsClient.querySpuBySpuId(id);
        if (spu == null) {
            log.error("索引对应的spu不存在，spuId:{}", id);
            throw new RuntimeException();
        }
        try {
            Goods goods = buildGoods(spu);
            //保存到索引库
            goodsRepository.save(goods);
        } catch (Exception e) {
            log.error("构建商品失败", e);
            throw new RuntimeException();
        }

    }

    /**
     * 删除索引
     *
     * @param id
     */
    public void delete(Long id) {
        goodsRepository.deleteById(id);
    }


    public void createOrUpdateIndex(Long spuId) {
         //查询spu
        Spu spu = goodsClient.querySpuBySpuId(spuId);
        //构建goods
        Goods goods = buildGoods(spu);
        //存入索引库
        goodsRepository.save(goods);

    }

    public void deleteIndex(Long spuId) {

        goodsRepository.deleteById(spuId);

    }
}