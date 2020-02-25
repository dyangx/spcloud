package com.cloud.user.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: yangjie
 * @date: Created in 2020/2/25 15:05
 */
@Component
public class Template {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void test(){
        System.out.println("*******************"+ rabbitTemplate);
    }
}
