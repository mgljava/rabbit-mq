package com.github.mgljava.rabbitmq.workqueue;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 工作队列：
 * 多个Worker进程并行工作，接受消息的平均分配（一次性分配消息）
 * 默认情况下，RabbitMQ会将会发送的消息按照顺序给下一个消费者，这种分发消息的方式叫做轮训调度
 */
public class Worker {

  private static final String QUEUE_NAME = "newTask";

  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("127.0.0.1");

    final Connection connection = connectionFactory.newConnection();
    final Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String message = new String(body, "UTF-8");

        System.out.println(" [x] Received '" + message + "'");
        try {
          doWork(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }

  private static void doWork(String task) throws InterruptedException {
    String[] taskArr = task.split(":");
    TimeUnit.SECONDS.sleep(Long.valueOf(taskArr[1]));
  }
}
