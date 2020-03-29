package com.cloud.common.config;

import com.cloud.common.service.CommonService;
import com.cloud.common.service.CommonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public CommonService getCommonService(){
        CommonService commonService = new CommonServiceImpl();
        System.out.println("dddddddddddddddddddddd");
        return commonService;
    }
}
