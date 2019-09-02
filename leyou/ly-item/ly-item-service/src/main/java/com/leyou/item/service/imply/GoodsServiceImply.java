package com.leyou.item.service.imply;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import com.leyou.item.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author river
 * @title: GoodsServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/717:58
 */
@Service
@Slf4j
public class GoodsServiceImply implements GoodsService{
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryServiceImply categoryServiceImply;
    @Autowired
    private BrandServiceImply brandServiceImply;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable) {
        //分页
        PageHelper.startPage(page, rows);
        //数据过滤---搜索过滤，上下架过滤
        Example exampleSpu = new Example(Spu.class);
        Example.Criteria exampleSpuCriteria = exampleSpu.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            exampleSpuCriteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            exampleSpuCriteria.andEqualTo(saleable);

        }
        //默认排序
        exampleSpu.setOrderByClause("last_update_time DESC");
        //查询
        List<Spu> spuList = spuMapper.selectByExample(exampleSpu);
        //判断
        if (CollectionUtils.isEmpty(spuList)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }

        //解析分类和品牌名称
        loadCategoryBrandName(spuList);

        // 解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spuList);
        return new PageResult<>(info.getTotal(), spuList);

    }

    private void loadCategoryBrandName(List<Spu> spuList) {
        for (Spu spu : spuList) {
            //处理分类名称
            List<String> categoryNames = categoryServiceImply.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());//保存结果到List
            spu.setCname(StringUtils.join(categoryNames, "/"));
            //处理分类品牌
            spu.setBname(brandServiceImply.queryById(spu.getBrandId()).getName());
        }
    }


    @Override
    public void saveGoods(Spu spu) {
        //新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setSaleable(true);
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setValid(false);
        int countSpu = spuMapper.insert(spu);
        if (countSpu != 1) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //新增detail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        //定义库存集合
        List<Stock> stockList = new ArrayList<>();
        //新增sku
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            int countSku = skuMapper.insert(sku);
            if (countSku != 1) {
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //批量增加
        stockMapper.insertList(stockList);

        //发送mqp消息
        amqpTemplate.convertAndSend("item.insert",spu.getId());
    }

    @Override
    public SpuDetail queryDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new LyException(ExceptionEnum.GOOD_SKU_NOT_FOUND);
        }

        //查询库存
        List<Long> skuIds = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(skuIds);
        if (CollectionUtils.isEmpty(stockList)) {
            throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
        }
        //把stock变成一个map，其key是：sku的id，值是库存值
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skuList.forEach(sku1 -> sku1.setStock(stockMap.get(sku1.getId())));
        return skuList;
    }

    @Override
    public void deleteGoodsBySpuId(Long spuId) {
        if (spuId == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        //删除spu，把spu中的valid字段设置成false
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setValid(false);
        int count = spuMapper.deleteByPrimaryKey(spu);
        if (count!=1){
            throw new LyException(ExceptionEnum.DELETE_GOODS_ERROR);
        }

        //发送mq消息
        amqpTemplate.convertAndSend("item.delete",spu.getId());
    }

    @Override
    public void updateGoods(Spu spu) {
        if(spu.getId()==null){
            throw  new LyException(ExceptionEnum.INVALID_PARAM);
        }
        //首先查询sku
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)){
             //删除所有sku
            List<Long> skuListIds = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            skuMapper.deleteByIdList(skuListIds);
            stockMapper.deleteByIdList(skuListIds);
        }
        //更新数据库
        spu.setLastUpdateTime(new Date());
        int countSpu = spuMapper.updateByPrimaryKey(spu);
        if (countSpu == 0) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        int countSpuDetail = spuDetailMapper.updateByPrimaryKey(spuDetail);
        if (countSpuDetail == 0) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        //更新sku和stock
        saveSkuAndStock(spu);

        //发送mqp消息
        amqpTemplate.convertAndSend("item.update",spu.getId());

    }

    //更新sku和stock
    private void saveSkuAndStock(Spu spu){
        List<Sku> skuList= spu.getSkus();
        List<Stock> stockList= new ArrayList<>();
        for (Sku sku : skuList) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            int countSkun = skuMapper.insert(sku);
            if (countSkun != 1) {
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
           Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //批量插入库存数据
        int count = stockMapper.insertList(stockList);
        if (count == 0) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        //发送mq消息
        amqpTemplate.convertAndSend("item.savae",spu.getId());

    }

    @Override
    public Spu querySpuBySpuId(Long id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //查询spu下的sku集合
        List<Sku> skuList = querySkuBySpuId(id);
        spu.setSkus(skuList);
        //查询detail
        SpuDetail spuDetail = queryDetailBySpuId(id);
        spu.setSpuDetail(spuDetail);
        return  spu;
    }

}
