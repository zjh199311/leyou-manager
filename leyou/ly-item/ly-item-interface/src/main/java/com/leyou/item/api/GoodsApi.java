package com.leyou.item.api;

import com.leyou.common.vo.PageResult;
import com.leyou.item.dto.CartDto;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author river
 * @title: GoodsApi
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:38
 */

public interface GoodsApi {

    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/goods/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 根据spu商品id查询详情
     *
     * @param id
     * @return
     */
    @GetMapping("/goods/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id") Long id);

    /**
     * 根据spu的id查询sku
     *
     * @param id
     * @return
     */
    @GetMapping("/goods/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);


    /**
     * 根据spuId查询spu及skus
     *
     * @param spuId
     * @return
     */
    @GetMapping("/goods/spu/{id}")
    Spu querySpuBySpuId(@PathVariable("id") Long spuId);


    /**
     * 减库存
     * @param cartDtos
     * @return
     */
    @PostMapping("/goods/stock/decrease")
    void decreaseStock(@RequestBody List<CartDto> cartDtos);


    /**
     * 根据sku ids查询sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/goods/sku/idList")
    List<Sku> querySkusByIds(@RequestParam("ids") List<Long> ids);

}
