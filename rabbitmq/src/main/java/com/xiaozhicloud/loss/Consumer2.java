package com.xiaozhicloud.loss;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

public class Consumer2 {
  public static final String TASK_QUEUE_NAME = "ack_queue";
  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();
    System.out.println("c2等持接收消息处理");

    DeliverCallback deliverCallback = (consumerTag, message) -> {
      Utils.sleep(30);
      System.out.println("接收消息"+ new String(message.getBody()));
      // 手动应答
      channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
    };

    // 取消消息
    CancelCallback cancelCallback = consumerTag -> {
      System.out.println("消费消息被中断");
    };

    boolean autoAck = false;

    channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,cancelCallback);

  }

}
