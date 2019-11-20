package com.cloud.movie.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 17:41
 */
@Component
@EnableBinding(MvInput.class)
public class MvInputService {

    @StreamListener(value = MvInput.MV_INPUT1)
    public void sendMessage1(String msg){
        System.out.println("1已推送消息:"+msg);
    }

    @StreamListener(value = MvInput.MV_INPUT2)
    public void sendMessage2(String msg){
        System.out.println("2已推送消息:"+msg);
    }
}
