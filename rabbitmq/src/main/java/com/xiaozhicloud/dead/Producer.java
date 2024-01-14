package com.xiaozhicloud.dead;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.xiaozhicloud.utils.Utils;

public class Producer {
  public static final String NORMAL_EXCHANGE = "normal_exchange";
  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();


    AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("100000").build();


    // 设置死信消息
    for(int i=1;i < 11;i++) {
      String message = "info" + i;
      channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes());
    }


  }

}
