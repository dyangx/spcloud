package com.cloud.rabbit.controller;

import com.cloud.common.service.CommonService;
import com.cloud.common.service.SimpleService;
import com.cloud.rabbit.publisher.Publisher;
import com.cloud.rabbit.service.RabbitService;
import com.cloud.rabbit.service.RabbitSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("rabbit")
public class RabbitContoller {

    @Autowired
    Publisher publisher;

    @Autowired
    CommonService commonService;

    @Autowired
    SimpleService simpleService;

//    @Autowired
    RabbitSimpleService rabbitSimpleService;

    @RequestMapping("push")
    public Object push(String msg,String type){
        System.out.println(new Date());
        publisher.publish(msg,type);
        return "ok!";
    }

    @RequestMapping("pushBatch")
    public Object pushBatch(){
        publisher.publishBatch();
        return "ok!";
    }

    @RequestMapping("common")
    public Object common(){
        commonService.say();
        return "sss";
    }

    @RequestMapping("simple")
    public Object simple(){
        simpleService.say();
        return "sss";
    }
}
