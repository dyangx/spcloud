package com.cloud.rabbit.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class RabbitSimpleService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public RabbitSimpleService(){
        System.out.println("SimpleService init...........");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void say(){
        System.out.println("SimpleService say........");
    }
}
