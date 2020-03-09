package com.cloud.txlcn;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagerServer
public class TxlcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxlcnApplication.class, args);
    }

}
