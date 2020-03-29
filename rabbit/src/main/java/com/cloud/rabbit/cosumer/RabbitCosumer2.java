package com.cloud.rabbit.cosumer;

import com.cloud.rabbit.deadQueue.RabbbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitCosumer2 {

//    @RabbitListener(queues = RabbbitConfig.LIND_QUEUE)
    public void customer(String data){
        log.error("->从消息队列获取消息 ：{RabbitCosumer2}"+data);
    }

    @RabbitListener(queues = RabbbitConfig.LIND_DEAD_QUEUE)
    public void customer2(String data){
        log.error("->从死信消息队列获取消息 ：{RabbitCosumer2}"+data);
    }
}
