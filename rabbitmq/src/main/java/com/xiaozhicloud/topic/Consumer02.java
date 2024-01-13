package com.xiaozhicloud.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

public class Consumer02 {
  public static final String EXCHANGE_NAME = "topic";
  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    // 声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME,"topic");

    // 声明队列
    String queueName ="Q2";
    channel.queueDeclare(queueName,false,false,false,null);
    channel.queueBind(queueName,EXCHANGE_NAME,"*.*.rabbit");
    channel.queueBind(queueName,EXCHANGE_NAME,"lazy.#");
    System.out.println("等待接收消息....");

    DeliverCallback deliverCallback = (consumerTag, message) -> {
      System.out.println("接收消息"+ new String(message.getBody()));
      System.out.println("接收消息" + queueName + "绑定键" + message.getEnvelope().getRoutingKey());
    };


    // 接收消息
    channel.basicConsume(queueName,true,deliverCallback,consumerTag -> {});
  }
}
