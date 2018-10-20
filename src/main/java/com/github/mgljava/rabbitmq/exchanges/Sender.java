package com.github.mgljava.rabbitmq.exchanges;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.Channel;

public class Sender {
  public static final String QUEUE_NAME = "world";
  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();

    String message = "Send Message Body";
    assert channel != null;
    /**
     * 四种 exchange类型
     * 1.direct
     * 2.topic
     * 3.headers
     * 4.fanout
     */
    channel.exchangeDeclare("logs", "fanout");
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.basicPublish("logs", "", null, message.getBytes());
  }
}
