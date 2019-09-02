package com.leyou.page.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: BrandClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:15
 */
@FeignClient(value = "item-service")
@Component
public interface  BrandClient extends BrandApi {
}
