package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author river
 * @title: UserClient
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/715:45
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi{





}
