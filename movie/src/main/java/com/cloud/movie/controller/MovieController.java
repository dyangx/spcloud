package com.cloud.movie.controller;

import com.cloud.movie.vo.Movie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/movie")
public class MovieController{

    @RequestMapping("/getMovie")
    public Movie getMovie(){
        Movie m = new Movie("321","呢抓","动画片",new Date());
        return m;
    }
}
