package com.xiaozhicloud.direct;

import com.rabbitmq.client.Channel;
import com.xiaozhicloud.utils.Utils;

import java.util.Scanner;

public class Producer {
  public static final  String EXCHANGE_NAME ="direct";
  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    channel.exchangeDeclare(EXCHANGE_NAME,"direct");

    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      String message = scanner.next();
      channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes("utf-8"));
      System.out.println("生产者成功发送消息");
    }
  }
}
