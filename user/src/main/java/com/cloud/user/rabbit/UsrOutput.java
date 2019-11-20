package com.cloud.user.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 17:50
 */
public interface UsrOutput {
    String USR_OUTPUT = "output";

    @Output(value = USR_OUTPUT)
    MessageChannel output();
}
