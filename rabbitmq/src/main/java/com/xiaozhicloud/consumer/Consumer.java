package com.xiaozhicloud.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.*;

public class Consumer {
  public static final String QUEUE_NAME = "hello";

  public static void main(String[] args) throws Exception {
    // 创建链接工厂
    ConnectionFactory factory = new ConnectionFactory();
    //设置ip地址
    factory.setHost("127.0.0.1");
    // 设置用户名
    factory.setUsername("guest");
    // 设置密码
    factory.setPassword("guest");

    // 创建连接
    Connection connection = factory.newConnection();
    // 创建信道

    Channel channel = connection.createChannel();
    DeliverCallback deliverCallback = (consumerTag,message) -> {
      System.out.println("接收消息"+ new String(message.getBody()));
    };

    CancelCallback cancelCallback = consumerTag -> {
      System.out.println("消费消息被中断");
    };

    // 消费者消费消息
    channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);


  }

}
