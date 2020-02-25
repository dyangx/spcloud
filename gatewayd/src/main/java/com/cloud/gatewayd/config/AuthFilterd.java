package com.cloud.gatewayd.config;

import com.cloud.gatewayd.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启全局Filter
 */
//@Configuration
public class AuthFilterd {

    @Bean
    public AuthFilter authSignatureFilter(){
        return new AuthFilter();
    }

}
