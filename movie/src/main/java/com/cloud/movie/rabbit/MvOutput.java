package com.cloud.movie.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息发送通道
 * @author: yangjie
 * @date: Created in 2019/11/20 17:38
 */

public interface MvOutput {
    String MV_OUTPUT1 = "output1";
    String MV_OUTPUT2 = "output2";

    @Output(MV_OUTPUT1)
    SubscribableChannel output1();

    @Output(MV_OUTPUT2)
    SubscribableChannel output2();
}
