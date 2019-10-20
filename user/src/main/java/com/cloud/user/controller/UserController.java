package com.cloud.user.controller;

import com.cloud.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

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
}
