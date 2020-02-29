package com.cloud.movie.controller;

import com.cloud.movie.rabbit.MvInputService;
import com.cloud.movie.service.TestService;
import com.cloud.movie.vo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/movie")
public class MovieController{

    @Value("${server.port}")
    private String port;

    @Autowired
    private MvInputService mvInputService;

    @Autowired
    private TestService testService;

    @RequestMapping("/getMovie")
    public Object getMovie() throws InterruptedException {
        Movie m = new Movie(port,"呢抓","动画片",new Date());
//        Thread.sleep(8000);
//        if(true) {
//            throw new RuntimeException("123");
//        }
        return m;
    }

    @RequestMapping("/pushMsg")
    public Object pushMsg(){
        for (int i=0;i<10;i++){
//            mvInputService.sendMessage1("来自msg1");
//            mvInputService.sendMessage2("来自msg2");
        }
        return "推送消息成功！";
    }

    @RequestMapping("/insert")
    public Object insert(){
        testService.test();
        return "ssss";
    }
}
