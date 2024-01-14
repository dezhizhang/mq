package com.xiaozhicloud.dead;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

public class Consumer02 {
  public static final String DEAD_QUEUE = "dead_queue";
  public static void main(String[] args) throws  Exception{
    Channel channel = Utils.getChannel();

    System.out.println("等待接收消息...");
    DeliverCallback deliverCallback = ((consumerTag, message) -> {
      System.out.println(new String(message.getBody()));
    });

    channel.basicConsume(DEAD_QUEUE,true,deliverCallback,consumerTag -> {});
  }
}
