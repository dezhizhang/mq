package com.xiaozhicloud.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Utils {
  // 连接工厂创建信道工具类
  public static Channel getChannel() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("127.0.0.1");
    factory.setUsername("guest");
    factory.setPassword("guest");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    return channel;
  }

  public static void sleep(int second) {
    try {
      Thread.sleep(1000 * second);
    } catch (InterruptedException _ignored) {
      Thread.currentThread().interrupt();
    }
  }
}
