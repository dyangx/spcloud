package com.cloud.rabbit.publisher;

import com.cloud.rabbit.deadQueue.RabbbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String msg,String type ) {
        Assert.notNull(msg,"msg must not be null");
        if(type != null){
            rabbitTemplate.convertAndSend(RabbbitConfig.LIND_DL_EXCHANGE, RabbbitConfig.LIND_DEAD_QUEUE,msg);
        }else {
            rabbitTemplate.convertAndSend(RabbbitConfig.LIND_EXCHANGE, RabbbitConfig.LIND_QUEUE,msg);
        }
    }

    public void publishBatch() {
        int i = 0;
        while (i < 50){
            rabbitTemplate.convertAndSend(RabbbitConfig.LIND_EXCHANGE, RabbbitConfig.LIND_QUEUE,"我是消息："+i);
            i++;
        }
    }

}
