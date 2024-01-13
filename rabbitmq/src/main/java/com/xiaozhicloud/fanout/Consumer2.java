package com.xiaozhicloud.fanout;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

public class Consumer2 {
  public static final String EXCHANGE_NAME = "logs";

  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

    // 创建一个临时队列
    String queueName = channel.queueDeclare().getQueue();

    // 绑定交换机与队列
    channel.queueBind(queueName,EXCHANGE_NAME,"");

    System.out.println("等待接收消息...");

    DeliverCallback deliverCallback = (consumerTag, message) -> {

      System.out.println("接收消息"+ new String(message.getBody()));
    };

    // 取消消息
    CancelCallback cancelCallback = consumerTag -> {
      System.out.println("消费消息被中断");
    };

    // 消费者接收消息
    channel.basicConsume(queueName,true,deliverCallback,cancelCallback);

  }
}
