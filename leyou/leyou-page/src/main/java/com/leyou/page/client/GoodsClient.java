package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author river
 * @title: GoodsClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:47
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {


}
