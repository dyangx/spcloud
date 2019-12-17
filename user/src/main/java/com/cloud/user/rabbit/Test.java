package com.cloud.user.rabbit;

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

    private static Connection connection;

    static {
        try {
            connection = new ConnectionFactory().newConnection("127.0.0.1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Test().basicPublish(connection);
    }

    // 拉模式获取消息
    private void basicGet(Connection connection){
        try (final Channel channel = connection.createChannel()) {
            GetResponse getResponse = channel.basicGet(QUEUE_NAME,false);
            doWork(getResponse.getBody());
            // 确认接受到消息
//            channel.basicAck(getResponse.getEnvelope().getDeliveryTag(),false);
            // 明确拒绝这个消息   requeue:true 消息重新放回队列，false 队列会删除消息，若为false可启用“死信队列”功能
            channel.basicReject(getResponse.getEnvelope().getDeliveryTag(),true);
            // 拒绝多条消息，推模式时使用  multiple:false 表示拒绝编号为deliveryTag的这一条消息，true 表示拒绝deliveryTag之前的所有消息
//            channel.basicNack(long deliveryTag,boolean multiple, boolean requeue,);
            // 重新分配消息 requeue:true 消息可能分配给宁一个消费者，false消息分配给之前相同的消费者，默认true
//            channel.basicRecover(boolean requeue);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 推模式获取消息
    private void basicPublish(Connection connection){
        try {
            // 创建信道
            final Channel channel = connection.createChannel();
            // queue
            channel.queueDeclare(QUEUE_NAME,DURABLE,false,false,null);
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body);
                    doWork(msg);
                    System.out.println(envelope.getRoutingKey());
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            // 需要消费消息确认
            boolean autoAck = false;
            // 每次只处理消息个数 ，在这里不起作用，只有在拉模式下才起作用
            channel.basicQos(10);
            channel.basicConsume(QUEUE_NAME,autoAck,consumer);
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end....");
    }

    private void doWork(String msg){
        System.out.println("接收到消息："+msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void doWork(byte[] msg){
        doWork(new String(msg));
    }
}
