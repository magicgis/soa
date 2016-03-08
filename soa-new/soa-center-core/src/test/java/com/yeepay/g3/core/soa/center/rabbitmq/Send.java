package com.yeepay.g3.core.soa.center.rabbitmq;

import com.rabbitmq.client.Connection;
import com.yeepay.g3.core.soa.center.entity.MonitorData;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/16.
 */
public class Send extends Thread{
    private String threadName;
    private RabbitMqUtils rabbitMqUtils = new RabbitMqUtils();
    private static Connection connection = RabbitMqUtils.connection;
    public Send(String threadName){
        this.threadName=threadName;
    }
    /**
     * 简单的异步发送消息实例
     */
    public void sendMessageSync(){
        for (int i = 0; i < 10; i++) {
            MonitorData monitorData = new MonitorData();
            monitorData.setType("provider");
            monitorData.setConcurrent(0);
            monitorData.setConsumer("50.1.1.21");
            String dateStr = new Date().toLocaleString().split(" ")[0].replace("-","");
            monitorData.setDateStr(dateStr);
            monitorData.setElapsed(10);
            monitorData.setFailure(0);
            monitorData.setMaxConcurrent(5);
            monitorData.setMaxElapsed(10);
            monitorData.setMethod("collect");
            monitorData.setService("com.alibaba.dubbo.monitor.MonitorService");
            monitorData.setProvider("50.1.1.22");
            monitorData.setSuccess(1);
            try {
                rabbitMqUtils.sendObjectToRabbitMq(monitorData, connection);
            }catch (Exception e){
                System.out.println("发送失败");
            }

        }
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(threadName + "开始运行第 " + i + " 次");
            //sendMessageAsync();
            sendMessageSync();
        }
    }
}
