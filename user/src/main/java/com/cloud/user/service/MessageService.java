package com.cloud.user.service;

import com.cloud.user.rabbit.UsrInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: yangjie
 * @date: Created in 2019/12/2 15:38
 */
@Component
@EnableBinding(UsrInput.class)
public class MessageService {

    @Autowired
    UsrInput usrInput;

 /*   @PostConstruct
    public void init(){

        SubscribableChannel subscribableChannel = usrInput.input();
        subscribableChannel.subscribe(message ->{
            byte[] b = (byte[]) message.getPayload();
            String s = new String(b);
            System.out.println("已经接收到消息：》》》"+s);
        });
    }*/

    @StreamListener(UsrInput.USR_INPUT)
    public void onMessage(byte[] data){
        System.out.println("byte[]:>>>" + new String(data));
    }

//    @StreamListener(UsrInput.USR_INPUT)
//    public void onMessage(String data){
//        System.out.println("String:>>>" + data);
//    }
//
//    @ServiceActivator(inputChannel = UsrInput.USR_INPUT)
//    public void onServiceActivator(String data){
//        System.out.println("onServiceActivator():>>>" +data);
//    }

    @StreamListener("hello_rabbit")
    public void onMessage2(byte[] data){
        System.out.println("byte[]:>>>" + new String(data));
    }

}
