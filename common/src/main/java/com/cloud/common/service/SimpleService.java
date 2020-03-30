package com.cloud.common.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

//@Component
public class SimpleService implements BeanFactoryAware {
    private BeanFactory beanFactory;

    public SimpleService(){
        System.out.println("SimpleService init...........");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("init factory......");
    }

    public void say(){
        System.out.println("SimpleService say........");
    }
}
