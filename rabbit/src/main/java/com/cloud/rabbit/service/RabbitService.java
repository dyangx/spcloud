package com.cloud.rabbit.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


public class RabbitService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化 InitializingBean。。。。。。。。。。。");
    }

    public void say(){
        System.out.println("RabbitService say...");
    }
}
