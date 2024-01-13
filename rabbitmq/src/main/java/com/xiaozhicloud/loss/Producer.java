package com.xiaozhicloud.loss;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xiaozhicloud.utils.Utils;

import java.util.Scanner;

public class Producer {
  public static final String TASK_QUEUE_NAME = "ack_queue";

  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    // 从控制台获取信息
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      String message = scanner.next();
      // 设置生产者发送消息为持久化
      channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
        message.getBytes("UTF-8"));
      System.out.println("生产者发出的消息" + message);
    }


  }
}
