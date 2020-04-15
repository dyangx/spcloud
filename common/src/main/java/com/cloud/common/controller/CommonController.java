package com.cloud.common.controller;

import com.cloud.common.service.DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yangjie
 * @date: Created in 2020/4/15 9:23
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    DService dService;

    @RequestMapping("/say")
    public Object say(){
        return dService.say();
    }

    @RequestMapping("${common.requrl}")
    public Object hou(){
        return dService.say();
    }

}
