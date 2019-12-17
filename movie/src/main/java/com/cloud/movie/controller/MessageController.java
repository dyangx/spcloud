package com.cloud.movie.controller;

import com.cloud.movie.rabbit.MvOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yangjie
 * @date: Created in 2019/12/2 15:13
 */
@RestController
@RequestMapping("/message")
@EnableBinding(MvOutput.class)
public class MessageController {

    @Autowired MvOutput mvOutput;

    @RequestMapping("/send")
    public String sendMsg(String s){
        SubscribableChannel mc = mvOutput.output1();

        for(int i = 0;i<1000;i++){
            GenericMessage genericMessage = new GenericMessage((s+":   "+i).getBytes());
            mc.send(genericMessage);
            System.out.println("已经发送消息："+ s);
        }
        return s;
    }
}
