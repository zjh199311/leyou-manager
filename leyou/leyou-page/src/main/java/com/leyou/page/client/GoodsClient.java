package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: GoodsClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:47
 */
@FeignClient(value = "item-service")
@Component
public interface GoodsClient extends GoodsApi {


}
