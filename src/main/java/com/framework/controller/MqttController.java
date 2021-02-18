package com.framework.controller;

import com.framework.mq.mqtt.MqttPushClient;
import com.framework.mq.mqtt.channel.MqttGateway;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 简要介绍 https://blog.csdn.net/qq_40674081/article/details/106815495
// 详细介绍 https://www.cnblogs.com/itdragon/p/12463050.html
@RestController
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    /**
     * 发送MQTT消息
     *
     * @param message 消息内容
     * @return 返回
     */

    @ResponseBody
    @GetMapping(value = "/mqtt")
    public ResponseEntity<String> mqtt(@RequestParam(value = "msg") String message) throws MqttException {
        String kdTopic = "topic1";
        MqttPushClient.getInstance().publish(kdTopic, "give me a break");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @RequestMapping("/sendMqtt")
    public String sendMqtt(String  sendData, String topic){
        mqttGateway.sendToMqtt(sendData,topic);
        return "OK";
    }

}
