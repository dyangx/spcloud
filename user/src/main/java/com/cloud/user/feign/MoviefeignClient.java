package com.cloud.user.feign;

import com.cloud.user.annotation.Cached;
import com.cloud.user.vo.Movie;
import com.cloud.user.vo.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  FeignClient 中name 是客户端的名称 ，用于创建robbon负载均衡器
 */
@FeignClient(name = "micro-provider-movie",contextId = "MoviefeignClient")
public interface MoviefeignClient {

    @RequestMapping("/movie/getMovie")
    @Cached(name = "getMovie",key = "movieId=#{#movieId}",expiry = 3*60)
    Object getMovie(String movieId);

    @RequestMapping(value = "/movie/getMv",method = RequestMethod.POST)
    @Cached(name = "getMovie",key = "movie=#{#movie.id},#{#movie.date}",expiry = 2*60)
    Object getv(Movie movie);

    @RequestMapping("/test/getHtml")
    ResponseEntity<String> getHtml();
}

/**
 *  feign 回退
 *
 */
@Component
class FeignClientFallback implements MoviefeignClient{

    @Override
    public Object getMovie(String hh) {
        Map<String,Object> map = new HashMap<>();
        map.put("mv","123456");
        map.put("date",new Date());
        return map;
    }

    @Override
    public Object getv(Movie movie) {
        return null;
    }

    @Override
    @Cacheable
    public ResponseEntity<String> getHtml() {
        return null;
    }
}
