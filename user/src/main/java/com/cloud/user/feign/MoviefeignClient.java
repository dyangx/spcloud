package com.cloud.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  FeignClient 中name 是客户端的名称 ，用于创建robbon负载均衡器
 */
@FeignClient(name = "micro-provider-movie")
public interface MoviefeignClient {

    @RequestMapping("/movie/getMovie")
    Object getMovie();

    @RequestMapping("/test/getHtml")
    ResponseEntity<String> getHtml();
}

/**
 *  feign 回退
 */
@Component
class FeignClientFallback implements MoviefeignClient{

    @Override
    public Object getMovie() {
        Map<String,Object> map = new HashMap<>();
        map.put("mv","123456");
        map.put("date",new Date());
        return map;
    }

    @Override
    public ResponseEntity<String> getHtml() {
        return null;
    }
}
