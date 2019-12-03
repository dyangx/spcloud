package com.cloud.movie.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: yangjie
 * @date: Created in 2019/11/20 13:53
 */
public class Test {

    private static final String QUEUE_NAME = "hello_rabbit";

    private static final boolean DURABLE = false;

    //需要发送的消息列表
    public static final String[] msgs = {"task 1", "task 2", "task 3", "task 4", "task 5", "task 6"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = null;
        Channel channel = null;
        connection = factory.newConnection();
        channel = connection.createChannel();

        // queue
        channel.queueDeclare(QUEUE_NAME,DURABLE,false,false,null);

        //推送消息
        for (int i=0;i<1000;i++){
            String s = "小消息："+i;
            channel.basicPublish("",QUEUE_NAME,null,s.getBytes());
            System.out.println("已推送消息："+s);
        }
        channel.close();
        connection.close();
    }
}
