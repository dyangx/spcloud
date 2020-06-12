package com.cloud.log.config;

import com.cloud.log.biz.MongoTemplateBiz;
import com.cloud.log.vo.Splog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = {"com.cloud.log.*"})
@ConditionalOnProperty(name = "sp.log.enabled",havingValue = "true")
public class ScanConfig {
}
