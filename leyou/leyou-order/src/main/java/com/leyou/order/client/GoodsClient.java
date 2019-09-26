package com.leyou.order.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author river
 * @title: GoodsClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2116:16
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi{
}
