package com.github.mgljava.rabbitmq.exchanges;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.Channel;
import java.io.IOException;

public class EmitLog {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] args) throws IOException {

    final Channel channel = MyConnectionManager.getInstance();

    // 1.指定一个交换器
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    // 2.发送消息
    String message = "Hello World, This is Exchanges test!";
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");
    // 3.关闭连接
    MyConnectionManager.closeResource();
  }
}
