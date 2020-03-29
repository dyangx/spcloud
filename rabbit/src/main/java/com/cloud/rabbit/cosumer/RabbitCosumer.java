package com.cloud.rabbit.cosumer;

import com.cloud.rabbit.deadQueue.RabbbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RabbitCosumer {

//    @RabbitListener(queues = RabbbitConfig.LIND_QUEUE)
    public void customer(String data){
        log.error("->从消息队列获取消息 ：{}"+data);
    }

//    @RabbitListener(queues = RabbbitConfig.LIND_DEAD_QUEUE)
    public void customer2(String data){
        log.error("->从死信消息队列获取消息 ：{}"+data);
    }

    @RabbitListener(queues = RabbbitConfig.LIND_QUEUE)
    public void customer(@Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException, InterruptedException {
        log.error("->从消息队列获取消息 ：{}"+message +":"+deliveryTag);
        if(message.indexOf("20") > -1){
            throw new RuntimeException("erro");
        }
        if(message.indexOf("3") > -1){
            // 消息拒收，进入死信队列
            channel.basicReject(deliveryTag,false);
        }
        if(message.indexOf("4") > -1){
            // false 处理单个 true批处理小于deliveryTag 之前的消息
            channel.basicAck(deliveryTag,false);
        }
        Thread.sleep(1000);
    }

}
