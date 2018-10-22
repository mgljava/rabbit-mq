package com.github.mgljava.rabbitmq.exchanges.fanout;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class FanoutConsumer {

  private static final String EXCHANGE_NAME = "fanout_logs";

  public static void main(String[] args) throws IOException {
    Channel channel = MyConnectionManager.getInstance();
    // 声明路由名字和类型
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    // 获取随机队列名称
    String queueName = channel.queueDeclare().getQueue();
    System.out.println("queueName is : " + queueName);

    // channel.queueDeclare(queueName, false, false, true, null);
    // 把队列绑定到路由上
    channel.queueBind(queueName, EXCHANGE_NAME, "");

    System.out.println(" Waiting for msg....");
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("Received msg is " + message);
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }
}
