package com.cloud.ntserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yangj
 * @date: Created in 2020/7/13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/isOK")
    public Object ok(){
        return "I am OK!";
    }
}
