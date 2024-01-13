package com.xiaozhicloud.work;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

public class Consumer {
  public static final String QUEUE_NAME = "hello";

  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();


    DeliverCallback deliverCallback = (consumerTag, message) -> {
      System.out.println("接收消息"+ new String(message.getBody()));
    };

    // 取消消息
    CancelCallback cancelCallback = consumerTag -> {
      System.out.println("消费消息被中断");
    };
    System.out.println("c1等待接收消息...");
    // 消费者消费消息
    channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

  }
}
