package com.cloud.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "micro-provider-shop")
public interface ShopFeignClient {

    @RequestMapping("/lcnShop/insert")
    String insertValue();
}
