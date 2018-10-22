package com.github.mgljava.rabbitmq.helloworld;

import static com.github.mgljava.rabbitmq.config.Constant.QUEUE_NAME;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * 消息消费者
 */
public class Received {

  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();

    assert channel != null;
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("Received message is : " + message);
      }
    };

    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
