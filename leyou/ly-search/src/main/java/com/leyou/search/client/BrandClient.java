package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: BrandClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:15
 */
@FeignClient("item-service")
@Component
public interface  BrandClient extends BrandApi {
}
