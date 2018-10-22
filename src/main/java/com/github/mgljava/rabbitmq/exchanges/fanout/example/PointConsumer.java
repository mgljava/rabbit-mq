package com.github.mgljava.rabbitmq.exchanges.fanout.example;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 处理用户积分
 */
public class PointConsumer {

  private static final String EXCHANGE_NAME = "img_handle_log";

  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        int points = getPoints(message);
        System.out.println("This message Container point is : " + points);
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }

  public static int getPoints(String message) {
    return Integer.valueOf(message.split(":")[1]) + 12;
  }
}
