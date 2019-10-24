package com.cloud.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  FeignClient 中name 是客户端的名称 ，用于创建robbon负载均衡器
 */
@FeignClient(name = "micro-provider-movie")
public interface MoviefeignClient {

    @RequestMapping("/movie/getMovie")
    public Object getMovie();
}
