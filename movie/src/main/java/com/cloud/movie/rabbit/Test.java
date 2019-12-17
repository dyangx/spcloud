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

    // 交换器持久化
    private static final boolean DURABLE = true;

    //需要发送的消息列表
    public static final String[] msgs = {"task 1", "task 2", "task 3", "task 4", "task 5", "task 6"};

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = null;
        Channel channel = null;
        connection = factory.newConnection();
        channel = connection.createChannel();

        // queue
        channel.queueDeclare(QUEUE_NAME,DURABLE,false,false,null);

        //持久化消息
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties.builder()
                .deliveryMode(2)  // 消息持久化
                .expiration("60000")  // TTL超时时间，不设置默认永不超时，设置为0表示此消息可以直接丢给消费者，否则消息将被丢弃，也可以在队列中设置
                .build();

//        pushMsgTx(channel,properties);
        pushMsgConfirm(channel,properties);

        channel.close();
        connection.close();
    }

    /** txSelect(),txCommit() 利用事务实现消息确认机制  */
    public static void pushMsgTx(Channel channel,AMQP.BasicProperties properties) throws IOException {
        // 将信道设置为事务模式
        channel.txSelect();
        for (int i=0;i<1000;i++){
            String s = "小消息："+i;
            channel.basicPublish("",QUEUE_NAME,properties,s.getBytes());
            // 确认事务提交
            channel.txCommit();
            System.out.println("已推送消息："+s);
        }
    }

    /**  发送方确认机制  **/
    public static void pushMsgConfirm(Channel channel, AMQP.BasicProperties properties) throws IOException, InterruptedException {
        // 将信道设置为publisher confirm 模式
        channel.confirmSelect();
        for (int i=0;i<1000;i++){
            String s = "小消息："+i;
            channel.basicPublish("",QUEUE_NAME,properties,s.getBytes());
            // 判断消息是否推送成功, 也可以批量判断
            if(!channel.waitForConfirms()){
                System.err.println("推送消息失败："+s);
            }else{
                System.out.println("已推送消息："+s);
            }
        }
    }
}
