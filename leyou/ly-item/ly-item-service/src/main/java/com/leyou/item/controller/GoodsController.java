package com.leyou.item.controller;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.dto.CartDto;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author river
 * @title: GoodsController
 * @projectName leyou
 * @description: key 代表搜索查询的关键字 saleable是否上下架
 * @date 2019/8/717:59
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable
    ) {
        return ResponseEntity.ok(goodsService.querySpuByPage(page, rows, key, saleable));

    }

    /**
     * 　　* @description: 新增商品
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/8 16:57
     *
     */
    @PostMapping("/saveGoods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 　　* @description: 根据spuid查询详情
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/8 21:42
     *
     */
    @RequestMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> queryDetailBySpuId(@PathVariable("spuId") Long spuId) {
        return ResponseEntity.ok(goodsService.queryDetailBySpuId(spuId));
    }

    /**
     * 　　* @description: 根据spuid查询sku的信息
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/8 21:56
     *
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("spuId") Long spuId) {
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }


    /**
     * 　　* @description: 根据spuId删除商品
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/9 10:09
     *
     */
    @DeleteMapping("spu/spuId/{spuId}")
    public ResponseEntity<Void> deleteGoodsBySpuId(@PathVariable("spuId") Long spuId) {
        goodsService.deleteGoodsBySpuId(spuId);
        return ResponseEntity.ok().build();
    }

    /**
     * 　　* @description: 更新商品
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/9 10:30
     *
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu) {
        goodsService.updateGoods(spu);
        return ResponseEntity.ok().build();
    }


    /**
     * 　　* @description: 通过spuid查询spu
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/16 10:00
     *
     */
    @GetMapping("/spu/{id}")
    public ResponseEntity<Spu> querySpuBySpuId(@PathVariable("id") Long id) {
        Spu spu = goodsService.querySpuBySpuId(id);
        if (spu == null) {
            throw new LyException(ExceptionEnum.SPU_NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * 根据sku ids查询sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/sku/idList")
    public ResponseEntity<List<Sku>> querySkusByIds(@RequestParam("ids") List<Long> ids) {
        return  ResponseEntity.ok(goodsService.querySkusByIds(ids));

    }

    /**
     * 减库存
     * @param cartDtos
     * @return
     */
    @PostMapping("stock/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDto> cartDtos){
        goodsService.decreaseStock(cartDtos);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
