package com.github.mgljava.rabbitmq.exchanges.fanout.example;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 清除图片缓存
 */
public class CacheConsumer {

  private static final String EXCHANGE_NAME = "img_handle_log";

  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");

    System.out.println(" Waiting for msg....");
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        String imageUrl = getImageUrl(message);
        System.out.println("This message Container ImageUrl is : " + imageUrl);
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }

  private static String getImageUrl(String message) {
    return message.split(":")[0];
  }
}
