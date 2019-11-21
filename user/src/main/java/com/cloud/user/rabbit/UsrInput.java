package com.cloud.user.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 接受通道
 * @author: yangjie
 * @date: Created in 2019/11/20 17:50
 */
public interface UsrInput {
    String USR_INPUT = "input";

    @Output(value = USR_INPUT)
    MessageChannel input();
}
