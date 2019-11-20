package com.cloud.movie.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 17:38
 */

public interface MvInput {
    String MV_INPUT1 = "input1";
    String MV_INPUT2 = "input2";

    @Input(MV_INPUT1)
    SubscribableChannel input1();

    @Input(MV_INPUT2)
    SubscribableChannel input2();
}
