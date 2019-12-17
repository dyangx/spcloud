package com.cloud.user.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 14:04
 */
public class Test {

    private static final String QUEUE_NAME = "hello_rabbit";

    private static final boolean DURABLE = false;

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        try {
            final Channel channel = factory.newConnection().createChannel();
            // queue
            channel.queueDeclare(QUEUE_NAME,DURABLE,false,false,null);
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body);
                    doWork(msg);
                }
            };
            boolean autoAck = true;
            channel.basicConsume(QUEUE_NAME,autoAck,consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void doWork(String msg){
        System.out.println("接收到消息："+msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
