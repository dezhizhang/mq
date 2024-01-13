package com.xiaozhicloud.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
  public static final String QUEUE_NAME = "hello";

  public static void main(String[] args) throws Exception {

    // 创建链接工厂
    ConnectionFactory factory = new ConnectionFactory();

    // 设置ip地址
    factory.setHost("127.0.0.1");
    // 设置用户名
    factory.setUsername("guest");
    // 设置密码
    factory.setPassword("guest");

    // 创建连接
    Connection connection = factory.newConnection();
    // 生成队列
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    // 发送消息
    String message = "hello world";
    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
    System.out.println("消息发送完毕");

  }

}
