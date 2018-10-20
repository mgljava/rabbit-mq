package com.github.mgljava.rabbitmq.workqueue;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AckWorker {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws IOException {

    Channel channel = MyConnectionManager.getInstance();
    assert channel != null;
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    final Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
        try {
          doWork(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          // 每次处理完成一个消息后，手动发送一次应答。
          channel.basicAck(envelope.getDeliveryTag(), false);
        }
      }
    };
    // 第二个参数，关闭自动应答机制。
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }

  private static void doWork(String task) throws InterruptedException {
    String[] taskArr = task.split(":");
    TimeUnit.SECONDS.sleep(Long.valueOf(taskArr[1]));
  }
}
