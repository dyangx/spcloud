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

    private final static String EXCHANGE = "exchange2020";
    private final static String ROUTINGKEY = "key2020";



    @PostConstruct
    public void test(){
        int x = 0;
        while (x <= 5){
            x++;
            rabbitTemplate.convertAndSend("",ROUTINGKEY,"message：" + x);
        }
    }
}
