package com.xiaozhicloud.confirmation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.xiaozhicloud.utils.Utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class Producer {
  public static void main(String[] args) throws Exception {
    publishMessageAsync();
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
  public static void publishMessageBatch() throws Exception {
    Channel channel = Utils.getChannel();

    String queueName = UUID.randomUUID().toString();
    channel.queueDeclare(queueName,true,false,false,null);

    // 开启发布确认
    channel.confirmSelect();

    // 开始时间
    long begin = System.currentTimeMillis();

    // 批量确认消息大小
    int batchSize = 100;

    for(int i=0;i < 1000;i++) {
      String message = i + "";
      channel.basicPublish("",queueName,null,message.getBytes());
      if(i % batchSize == 0) {
        channel.waitForConfirms();
      }
    }
    long end = System.currentTimeMillis();
    System.out.println("用时:"+ (end - begin));

  }

  // 异点发步确认
  public static void publishMessageAsync() throws Exception {
    Channel channel = Utils.getChannel();

    String queueName = UUID.randomUUID().toString();

    channel.queueDeclare(queueName,true,false,false,null);

    channel.confirmSelect();

    // 线程安全有序哈希表
    ConcurrentHashMap<Long,String> outstandingConfirms = new ConcurrentHashMap<>();


    // 开始时间
    long begin = System.currentTimeMillis();

    // 消息确认成功回调
    ConfirmCallback ackCallback = (deliveryTag, multiple) ->{
      // 删除确认消息
//      if(multiple) {
//        ConcurrentNavigableMap<Long,String> confirmed = outstandingConfirms.headMap(deliveryTag);
//        confirmed.clear();
//        return;
//      }
      outstandingConfirms.remove(deliveryTag);

      System.out.println("确认消息" + deliveryTag);
    };

    // 消息确认成功回调
    ConfirmCallback nackCallback =(deliveryTag, multiple ) -> {
        System.out.println(outstandingConfirms.get(deliveryTag));
        System.out.println("未确认消息" + deliveryTag);
    };

    channel.addConfirmListener(ackCallback,nackCallback);


    for(int i=0;i < 1000;i++) {
      String message = "消息" + i;
      channel.basicPublish("",queueName,null,message.getBytes());
      outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
    }



    long end = System.currentTimeMillis();
    System.out.println("异步发布确认" + (end - begin) + "ms");


  }

}
