package com.xiaozhicloud.confirmation;

import com.rabbitmq.client.Channel;
import com.xiaozhicloud.utils.Utils;

import java.util.UUID;

public class Producer {
  public static void main(String[] args) throws Exception {
    publishMessageIndividually();
  }

  public static void publishMessageIndividually() throws Exception {
    Channel channel = Utils.getChannel();
    String queueName = UUID.randomUUID().toString();

    channel.queueDeclare(queueName,true,false,false,null);

    // 开启发布确认
    channel.confirmSelect();
    long begin = System.currentTimeMillis();

    for(int i=0;i < 1000;i++) {
      String message = i +"";
      channel.basicPublish("",queueName,null,message.getBytes());
      // 单个消息马上进行发布确认
      boolean flag = channel.waitForConfirms();
      if(flag) System.out.println("消息发布成功");

    }

    long end = System.currentTimeMillis();
    System.out.println("用时:"+ (end - begin));


  }

}
