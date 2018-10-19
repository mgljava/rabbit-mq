package com.github.mgljava.rabbitmq;

import static com.github.mgljava.rabbitmq.Constant.HOST;
import static com.github.mgljava.rabbitmq.Constant.QUEUE_NAME;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生产者
 */
public class Sender {

  public static void main(String[] args) throws Exception {

    // 创建连接
    ConnectionFactory connectionFactory = new ConnectionFactory();
    // 设置主机
    connectionFactory.setHost(HOST);

    // 建立连接
    Connection connection = connectionFactory.newConnection();
    // 创建管道
    Channel channel = connection.createChannel();
    // 指定一个队列
    /**
     * 参数1 queue ：队列名
     * 参数2 durable ：是否持久化
     * 参数3 exclusive ：仅创建者可以使用的私有队列，断开后自动删除
     * 参数4 autoDelete : 当所有消费客户端连接断开后，是否自动删除队列
     * 参数5 arguments
     */
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    // 发送的文本
    String message = "Welcome";

    /**
     * 参数1 exchange ：交换器
     * 参数2 routingKey ： 路由键
     * 参数3 props ： 消息的其他参数
     * 参数4 body ： 消息体
     */
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

    System.out.println("Message Send Success : " + message);

    channel.close();
    connection.close();
  }
}
