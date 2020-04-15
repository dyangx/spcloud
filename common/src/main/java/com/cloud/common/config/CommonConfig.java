package com.cloud.common.config;

import com.cloud.common.service.CommonService;
import com.cloud.common.service.CommonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.cloud.common.controller","com.cloud.common.service"})
public class CommonConfig {

   /* @Bean
    public CommonService getCommonService(){
        CommonService commonService = new CommonServiceImpl();
        System.out.println("dddddddddddddddddddddd");
        return commonService;
    }*/
}
