package com.cloud.movie.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

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
