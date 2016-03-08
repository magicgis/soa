package com.yeepay.g3.core.soa.center.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yeepay.g3.utils.common.json.JSONUtils;

/**
 * Created by Administrator on 2015/2/3.
 */
public class RabbitMqUtils {

    public static Connection connection;

    private static Connection getInstance() {
        if (connection == null) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("rabbitmq.bass.3g");
            connectionFactory.setPort(56720);
            connectionFactory.setUsername("soa");
            connectionFactory.setPassword("Zj4xyBkgjd-soa");
            connectionFactory.setVirtualHost("soa");
            try {
                connection = connectionFactory.newConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 将对象发送到RabbitMQ服务上
     *
     * @param object
     * @param connection
     */
    public void sendObjectToRabbitMq(Object object, Connection connection) throws Exception {
        if (connection == null) {
            connection = getInstance();
        }
        Channel channel = null;
        channel = connection.createChannel();
        channel.basicPublish("", "soa.rmi.monitor", null, JSONUtils.toJsonString(object).getBytes());
        //关闭通道
        if (channel != null) {
            channel.close();
        }
    }
}
