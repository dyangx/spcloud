package com.cloud.movie.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息发送通道
 * @author: yangjie
 * @date: Created in 2019/11/20 17:38
 */

public interface MvOutput {
    String MV_OUTPUT1 = "mv_msg11";

    @Output(MV_OUTPUT1)
    SubscribableChannel output1();
}
