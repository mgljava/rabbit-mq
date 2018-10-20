package com.github.mgljava.rabbitmq.workqueue;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.Channel;

public class AckNewTask {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws Exception {

    final Channel channel = MyConnectionManager.getInstance();
    assert channel != null;
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // 发送消息
    for (int i = 0; i < 20; i++) {
      String message = "Hello:" + i;
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
      System.out.println(" [x] Sent '" + message + "'");
    }
    MyConnectionManager.closeResource();
  }
}
