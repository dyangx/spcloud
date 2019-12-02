package com.cloud.user.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 接受通道
 * @author: yangjie
 * @date: Created in 2019/11/20 17:50
 */
public interface UsrInput {
    String USR_INPUT = "mv_msg11";

    @Input(value = USR_INPUT)
    SubscribableChannel input();

    @Input(value = "hello_rabbit")
    SubscribableChannel input2();
}
