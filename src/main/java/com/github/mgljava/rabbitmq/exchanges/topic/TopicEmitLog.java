package com.github.mgljava.rabbitmq.exchanges.topic;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.UUID;

/**
 * 直接转发 -> 生产者
 */
public class TopicEmitLog {

  private static final String EXCHANGE_NAME = "topic_logs";
  private static final String[] LOG_LEVEL_ARR = {"dao.debug", "dao.info", "dao.error",
      "service.debug", "service.info", "service.error",
      "controller.debug", "controller.info", "controller.error"};

  public static void main(String[] args) throws IOException {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

    // 发送消息
    for (String routeKey : LOG_LEVEL_ARR) {
      String message = "Send log : [" + routeKey + "]" + UUID.randomUUID().toString();
      // 发布消息至交换器
      channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
      System.out.println(" [x] Sent '" + message + "'");
    }

    MyConnectionManager.closeResource();
  }
}
