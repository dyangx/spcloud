package com.cloud.gatewayd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewaydApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaydApplication.class, args);
    }

}
