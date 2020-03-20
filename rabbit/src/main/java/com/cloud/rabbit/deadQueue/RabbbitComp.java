package com.cloud.rabbit.deadQueue;

import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2020/3/20 16:26
 *以下三种情况下会变成死信:
 * 1.消息被拒绝（basic.reject 或者 basic.nack),并且requeue=false
 * 2.消息时间过期
 * 3.队列消息长度超过限制
 * 当队列中的消息成为死信以后，如果队列设置了DLX那么消息会被发送到DLX。通过x-dead-letter-exchange设置DLX，
 * 通过这个x-dead-letter-routing-key设置消息发送到DLX所用的routing-key，如果不设置默认使用消息本身的routing-key
 */
@Component
public class RabbbitComp {

    private static final String lind_exchange = "lind.exchange";
    private static final String lind_dl_exchange = "lind.dl.exchange";
    private static final String lind_queue = "lind.queue";
    private static final String lind_dead_queue = "lind.dead.queue";
    private static final String lind_fanout_exchange = "lindFanoutExchange";

}
