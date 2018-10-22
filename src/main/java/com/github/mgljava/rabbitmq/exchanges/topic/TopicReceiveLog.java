package com.github.mgljava.rabbitmq.exchanges.topic;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.Random;

/**
 * 直接转发 -> 接受者
 */
public class TopicReceiveLog {

  private static final String EXCHANGE_NAME = "topic_logs";
  private static final String[] LOG_LEVEL_ARR = {"#", "dao.error",
      "*.error", "dao.*", "service.#", "*.controller.#"};

  public static void main(String[] args) throws IOException {

    Channel channel = MyConnectionManager.getInstance();
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

    int random = new Random().nextInt(LOG_LEVEL_ARR.length);
    String routeKey = LOG_LEVEL_ARR[random];
    System.out.println("routeKey is : " + routeKey);

    final String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE_NAME, routeKey);

    final Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String message = new String(body, "UTF-8");

        System.out.println(" [x] Received '" + message + "'");
      }
    };

    channel.basicConsume(queueName, true, consumer);
  }
}
