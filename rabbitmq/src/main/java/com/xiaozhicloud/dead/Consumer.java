package com.xiaozhicloud.dead;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaozhicloud.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
  public static final String NORMAL_EXCHANGE = "normal_exchange";
  public static final String DEAD_EXCHANGE = "dead_exchange";

  public static final String NORMAL_QUEUE = "normal_queue";

  public static final String DEAD_QUEUE = "dead_queue";

  public static void main(String[] args) throws Exception {
    Channel channel = Utils.getChannel();

    // 声明列信和普通交换机类型为direct
    channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
    channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

    Map<String,Object> arguments = new HashMap<>();

    // 过期时间
    arguments.put("x-message-ttl",100000);

    // 正常队列设置死信交换机
    arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);

    arguments.put("x-dead-letter-routing-key","lisi");

    // 普通队列与死信队列
    channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);

    // 死信队列
    channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
    // 邦定普通交换机与普通队列
     channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
     channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");

     System.out.println("等待接收消息");

    DeliverCallback deliverCallback = ((consumerTag, message) -> {
        System.out.println(new String(message.getBody(),"utf-8"));
    });

    channel.basicConsume(NORMAL_QUEUE,true,deliverCallback,consumerTag -> {});

  }
}
