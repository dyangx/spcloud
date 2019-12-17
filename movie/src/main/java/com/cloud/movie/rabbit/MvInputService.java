package com.cloud.movie.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 17:41
 */
@Component
@EnableBinding(MvOutput.class)
public class MvInputService {

/*    @StreamListener(value = MvOutput.MV_OUTPUT1)
    @SendTo(MvOutput.MV_OUTPUT1)/
    public void sendMessage1(String msg){
        System.out.println("1已推送消息:"+msg);
    }*/

//    @StreamListener(value = MvOutput.MV_OUTPUT2)
//    public void sendMessage2(String msg){
//        System.out.println("2已推送消息:"+msg);
//    }
}
