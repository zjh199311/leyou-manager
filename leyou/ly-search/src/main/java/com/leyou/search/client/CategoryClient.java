package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: CategoryClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1214:36
 */
@FeignClient("item-service")
@Component
public interface CategoryClient extends CategoryApi{
}
