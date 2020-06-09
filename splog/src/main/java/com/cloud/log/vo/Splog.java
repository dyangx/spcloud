package com.cloud.log.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9 14:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "sp.log")
public class Splog{

    private Boolean enabled = Boolean.FALSE;

    private Boolean printInfo = Boolean.TRUE;


}
