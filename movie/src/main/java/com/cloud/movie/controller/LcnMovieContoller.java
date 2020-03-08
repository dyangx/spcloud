package com.cloud.movie.controller;

import com.cloud.movie.service.LcnMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lcnMovie")
public class LcnMovieContoller {

    @Autowired
    private LcnMovieService lcnMovieService;

    @RequestMapping("insertValue")
    public String insertValue(){
        return lcnMovieService.insertValue();
    }
}
