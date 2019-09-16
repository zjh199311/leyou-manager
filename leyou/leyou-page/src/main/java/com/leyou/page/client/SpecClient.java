package com.leyou.page.client;

import com.leyou.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author river
 * @title: SpecClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:46
 */
@FeignClient(value = "item-service")
public interface SpecClient extends SpecApi {
}

