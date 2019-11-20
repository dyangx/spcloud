package com.cloud.user.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 17:52
 */

@Component
@EnableBinding(UsrOutput.class)
public class UsrOutputService {

    @StreamListener()
    public void receive(){

    }
}
