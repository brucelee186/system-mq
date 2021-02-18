package com.framework.mq.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


// 基于https://blog.csdn.net/weixin_38261597/article/details/89510270
@Slf4j
@Component
public class MqttReceive implements MqttCallback{

    private final static Logger log = LoggerFactory.getLogger(MqttReceive.class);

    @Value("${mqtt.url}")
    public String url;

    @Value("${mqtt.consumer.defaultTopic}")
    public String defaultTopic;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.consumer.clientId}")
    private String clientId;

    private MqttClient client;

    private MqttConnectOptions options;

    //clientId不能重复所以这里我设置为系统时间
    String clientUnique = clientId + String.valueOf(System.currentTimeMillis());

    @PostConstruct
    public void result() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(url, clientUnique, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(username);
            // 设置连接的密码
            options.setPassword(password.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(3600);
            // 设置回调
            client.setCallback(this);
            client.connect(options);
            //订阅消息
            int[] Qos = {1};
            String[] topic1 = {defaultTopic};
            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            log.info("ReportMqtt客户端连接异常，异常信息：" + e);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        try {
            log.info("程序出现异常，DReportMqtt断线！正在重新连接...:");
            client.close();
            this.result();
            log.info("ReportMqtt重新连接成功");
        } catch (MqttException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("接收消息主题:" + topic);
        log.info("接收消息Qos:" + message.getQos());
        log.info("接收消息内容 :" + new String(message.getPayload()));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("消息发送成功");
    }
}