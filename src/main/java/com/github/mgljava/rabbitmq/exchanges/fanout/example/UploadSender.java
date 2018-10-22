package com.github.mgljava.rabbitmq.exchanges.fanout.example;

import com.github.mgljava.rabbitmq.factory.MyConnectionManager;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * 需求：
 * 生产者：
 *  1.用户上传图片到图片服务器
 * 消费者：
 *  1.清除原有的图片缓存信息
 *  2.赠送积分
 */
public class UploadSender {

  private static final String EXCHANGE_NAME = "img_handle_log";

  public static void main(String[] args) throws Exception {

    Channel channel = MyConnectionManager.getInstance();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    String message = "heep://imgUrl:80";
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    System.out.println("Sent msg is '" + message + "'");
    MyConnectionManager.closeResource();
  }
}
