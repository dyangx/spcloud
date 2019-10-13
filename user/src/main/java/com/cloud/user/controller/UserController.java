package com.cloud.user.controller;

import com.cloud.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUser")
    public User getUser(){
        User u = new User("123","snoopy","18380807927","四川成都");
        return u;
    }

    @RequestMapping("/getMovieFromUser")
    public Object getMovie(){
        return restTemplate.getForObject("http://localhost:8082/movie/getMovie",Object.class);
    }
}
