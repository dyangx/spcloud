package com.cloud.rabbit.controller;

import com.cloud.rabbit.publisher.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("rabbit")
public class RabbitContoller {

    @Autowired
    Publisher publisher;

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
}
