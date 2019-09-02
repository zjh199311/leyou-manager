package com.leyou.item.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author river
 * @title: GoodsService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/717:58
 */
public interface GoodsService {
    PageResult<Spu> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable);

    void saveGoods(Spu spu);

     SpuDetail queryDetailBySpuId(Long spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void deleteGoodsBySpuId(Long spuId);

    void updateGoods(Spu spu);

   Spu querySpuBySpuId(Long id);
}
