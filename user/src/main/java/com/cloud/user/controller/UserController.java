package com.cloud.user.controller;

import com.cloud.user.feign.MoviefeignClient;
import com.cloud.user.vo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private MoviefeignClient moviefeignClient;

    @RequestMapping("/getUser")
    public User getUser(){
        User u = new User("123","snoopy","18380807927","四川成都");
        return u;
    }

    @RequestMapping(value = "/getMovieFromUser")
    public Object getMovie(){
        Object o = restTemplate.getForObject("http://micro-provider-movie/movie/getMovie",Object.class);
        return o;
    }

    @RequestMapping(value = "/showInfo")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("micro-provider-movie");
    }

    @RequestMapping(value = "/log-user-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("micro-provider-movie");
        String s = serviceInstance.getServiceId() + serviceInstance.getHost() +serviceInstance.getPort();
        System.out.println(s);
    }

    @RequestMapping("/getMoiveFromFeign")
    public Object getMoiveFromFeign(){
        return moviefeignClient.getMovie();
    }

    /**
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "getMovieHystrix",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")},
            threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "1"),
            @HystrixProperty(name = "maxQueueSize",value = "10")
    })
    @RequestMapping("/getMoiveFromFeignWithHystrix")
    public Object getMoiveFromFeignWithHystrix(){
        return moviefeignClient.getMovie();
    }

    public Object getMovieHystrix(){
        Map<String,Object> map = new HashMap<>();
        map.put("mv","123456");
        map.put("date",new Date());
        return map;
    }

    //zuul http://localhost:8040/micro-provider-user/user/getMoiveFromFeignWithHystrix
}
