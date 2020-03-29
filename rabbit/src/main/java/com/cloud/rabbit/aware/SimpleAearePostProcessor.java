package com.cloud.rabbit.aware;

import com.cloud.rabbit.service.RabbitSimpleService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SimpleAearePostProcessor implements BeanPostProcessor {

    private RabbitSimpleService rabbitSimpleService;

    public SimpleAearePostProcessor(RabbitSimpleService rabbitSimpleService){
        System.out.println("SimpleAearePostProcessor init");
        this.rabbitSimpleService = rabbitSimpleService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof RabbitSimpleService){
            System.out.println("before");
//            ((RabbitSimpleService) bean).setBeanFactory();
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after");
        return bean;
    }
}
