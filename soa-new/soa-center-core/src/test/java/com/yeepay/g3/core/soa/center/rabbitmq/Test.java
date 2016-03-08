package com.yeepay.g3.core.soa.center.rabbitmq;

/**
 * Created by Administrator on 2015/1/22.
 */
public class Test {
    public static void main(String[] args) throws Exception{
       //Send send1 =new Send("发送方1");
       //send1.start();
        ImitateSendMonitorData imitateSendMonitorData = new ImitateSendMonitorData();
        Thread t1 = new Thread(imitateSendMonitorData);
        t1.start();
        Thread t2 = new Thread(imitateSendMonitorData);
        t2.start();
        //Receive receive1 =new Receive("接收方");
        //receive1.start();
        //MsgUtils.init();
    }
}
