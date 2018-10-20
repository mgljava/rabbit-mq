package com.github.mgljava.rabbitmq.factory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyConnectionManager {

  private static Channel channel;
  private static Connection connection;

  public static Channel getInstance() {
    // 创建连接
    ConnectionFactory connectionFactory = new ConnectionFactory();
    // 设置主机
    connectionFactory.setHost("127.0.0.1");
    try {
      // 建立连接
      connection = connectionFactory.newConnection();
      // 创建管道
      channel = connection.createChannel();
      return channel;
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void closeResource() {
    if (channel != null) {
      try {
        channel.close();
      } catch (IOException | TimeoutException e) {
        e.printStackTrace();
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
