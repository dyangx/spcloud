package com.cloud.rabbit.deadQueue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class RabbbitConfig {

    public static final String LIND_EXCHANGE = "lind.exchange";
    public static final String LIND_DL_EXCHANGE = "lind.dl.exchange";
    public static final String LIND_QUEUE = "lind.queue";
    public static final String LIND_DEAD_QUEUE = "lind.dead.queue";
    public static final String LIND_FANOUT_EXCHANGE = "lindFanoutExchange";

    /**
     *  过期时间 微秒
     */
    private long callExpire = 10000;

    /**
     * 创建普通交换机
     */
    @Bean
    public TopicExchange lindExchange(){
        return (TopicExchange) ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true).build();
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public TopicExchange lindExchangeDl(){
        return (TopicExchange) ExchangeBuilder.topicExchange(LIND_DL_EXCHANGE).durable(true).build();
    }

    /**
     * 普通队列
     */
    @Bean
    public Queue lindQueue(){
        return QueueBuilder.durable(LIND_QUEUE)
                .withArgument("x-dead-letter-exchange",LIND_DL_EXCHANGE) // 设置死信交换机
                .withArgument("x-message-ttl",callExpire)   // 超时时间
                .withArgument("x-dead-letter-routing-key",LIND_DEAD_QUEUE) // 设置死信队列的路由键
                .build();
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue lindDelayQueue(){
        return QueueBuilder.durable(LIND_DEAD_QUEUE).build();
    }

    /**
     * 绑定普通队列
     */
    @Bean
    public Binding bindBuilders(){
        return BindingBuilder.bind(lindQueue()).to(lindExchange()).with(LIND_QUEUE);
    }

    /**
     * 绑定死信队列
     */
    @Bean
    public Binding bindDeadBuilders(){
        return BindingBuilder.bind(lindDelayQueue()).to(lindExchangeDl()).with(LIND_DEAD_QUEUE);
    }

    /**
     * 广播交换机
     */
//    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(LIND_FANOUT_EXCHANGE);
    }






}
