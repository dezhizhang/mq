package com.xiaozhicloud.loss;

import com.rabbitmq.client.Channel;
import com.xiaozhicloud.utils.Utils;

import java.util.Scanner;

public class Producer {
  public static final String TASK_QUEUE_NAME = "ack_queue";

  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

    // 从控制台获取信息
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      String message = scanner.next();
      channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes("UTF-8"));
      System.out.println("生产者发出的消息" + message);
    }


  }
}
