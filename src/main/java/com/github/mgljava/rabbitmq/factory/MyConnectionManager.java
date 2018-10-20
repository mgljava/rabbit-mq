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
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("127.0.0.1");
    try {
      connection = connectionFactory.newConnection();
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
