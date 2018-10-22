package com.github.mgljava.rabbitmq.exchanges.fanout;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;

/**
 * 该类型交换机的消息都会被广播到与该交换机绑定的所有队列上
 */
public class FanoutProducer {

  private static final String EXCHANGE_NAME = "fanout_logs";

  public static void main(String[] args) throws IOException {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    String message = "Fanout Type Test Message!";

    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    System.out.println("Sent msg is '" + message + "'");
    MyConnectionManager.closeResource();
  }
}
