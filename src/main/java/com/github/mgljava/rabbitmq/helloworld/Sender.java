package com.github.mgljava.rabbitmq.helloworld;

import static com.github.mgljava.rabbitmq.config.Constant.QUEUE_NAME;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * 消息生产者
 */
public class Sender {

  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();
    // 指定一个队列
    /**
     * 参数1 queue ：队列名
     * 参数2 durable ：是否持久化
     * 参数3 exclusive ：仅创建者可以使用的私有队列，断开后自动删除
     * 参数4 autoDelete : 当所有消费客户端连接断开后，是否自动删除队列
     * 参数5 arguments
     */
    assert channel != null;
    // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // 我们需要将其声明为持久化的。
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    /**
     * 公平转发:告诉 RabbitMQ 不要一次给一个工作线程多个消息。
     * 换句话说，在处理并确认前一个消息之前，不要向工作线程发送新消息。相反，它将发送到下一个还不忙的工作线程。
     */
    int prefetchCount = 1;
    channel.basicQos(prefetchCount);

    // 发送的文本
    String message = "Welcome";
    /**
     * 参数1 exchange ：交换器
     * 参数2 routingKey ： 路由键
     * 参数3 props ： 消息的其他参数
     * 参数4 body ： 消息体
     */
    // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    // 我们需要标识我们的信息为持久化的。通过设置 MessageProperties 值为 PERSISTENT_TEXT_PLAIN。
    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

    System.out.println("Message Send Success : " + message);

    MyConnectionManager.closeResource();
  }
}
