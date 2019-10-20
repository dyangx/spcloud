package com.cloud.movie.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 负载均衡配置
 *
 */
//@Configuration
public class RibbonClientConfiguration {

//    @Bean
    public IRule ribbonRuble(){

        //随机
        return new RandomRule();

        //选择一个最小的并发请求的server
//        return new BestAvailableRule();
    }
}
