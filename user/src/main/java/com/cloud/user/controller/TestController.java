package com.cloud.user.controller;

import com.cloud.user.feign.MoviefeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private MoviefeignClient moviefeignClient;

    @RequestMapping("/getHtml")
    public ResponseEntity<String> getHtml(){
        return moviefeignClient.getHtml();
    }
}
