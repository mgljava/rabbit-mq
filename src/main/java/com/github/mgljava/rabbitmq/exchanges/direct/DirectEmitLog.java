package com.github.mgljava.rabbitmq.exchanges.direct;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * 直接转发 -> 生产者
 */
public class DirectEmitLog {

  private static final String EXCHANGE_NAME = "direct_logs";
  private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};

  public static void main(String[] args) throws IOException {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

    for (int i = 0; i < 10; i++) {
      int random = new Random().nextInt(3);
      String routeKey = LOG_LEVEL_ARR[random];
      String message = "Send Log is : [" + routeKey + "]" + UUID.randomUUID().toString();
      channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
      System.out.println(" [x] Sent '" + message + "'");
    }

    MyConnectionManager.closeResource();
  }
}
