package com.leyou.search.client;

import com.leyou.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: SpecClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:46
 */
@FeignClient("item-service")
@Component
public interface SpecClient extends SpecApi {
}

