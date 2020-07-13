package com.cloud.ntserver;

import com.cloud.ntserver.server.PushServer;
import com.cloud.ntserver.socket.RunSocket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NtServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NtServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.err.println("googogo!!!!!");

        PushServer pushServer = new PushServer();
        pushServer.bind();
        new Thread(new RunSocket(pushServer)).start();

    }
}
