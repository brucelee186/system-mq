package com.framework.mq.mqtt;

import com.framework.util.UtilSpringContext;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

// 基于https://blog.csdn.net/weixin_38261597/article/details/89510270
// https://blog.csdn.net/csdnzhang365/article/details/105379173/

public class MQttPushCallback implements MqttCallback{


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开，可以做重连");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            System.out.println("接收消息主题 : " + topic);
            System.out.println("接收消息Qos : " + message.getQos());
            String json = new String(message.getPayload());
            System.out.println("接收消息内容 : " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(token);
        try {
            System.out.println(token.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}