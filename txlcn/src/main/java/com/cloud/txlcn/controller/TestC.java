package com.cloud.txlcn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestC {

    @RequestMapping("/you")
    public String test(){
        return "hello!";
    }
}
