package com.cloud.movie.controller;

import com.cloud.movie.vo.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/movie")
public class MovieController{

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getMovie")
    public Object getMovie(){
        Movie m = new Movie(port,"呢抓","动画片",new Date());
        return m;
    }
}
