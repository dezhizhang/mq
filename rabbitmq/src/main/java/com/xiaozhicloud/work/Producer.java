package com.xiaozhicloud.work;

import com.rabbitmq.client.Channel;
import com.xiaozhicloud.utils.Utils;

import java.util.Scanner;

public class Producer {

  public static final String QUEUE_NAME = "hello";

  public static void main(String[] args) throws  Exception{
    Channel channel = Utils.getChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);


    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      String message = scanner.next();
      channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
      System.out.println("消息发送完毕" + message);
    }

  }
}
