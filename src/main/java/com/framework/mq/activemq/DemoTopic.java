package com.framework.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

/**
 * Topic
 *
 * there must a topic for subscribe, and publisher could sent message to the topic subscribe consumer
 * one message could be consumer multiple second depend on the topic subscribe
 */
public class DemoTopic {
    private String userName = "neo";
    private String password = "admin";
    private String url = "tcp://192.168.1.25:61616";

    @Test
    public void publish() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // create session by topic
        Topic topic = session.createTopic("topic-mode-3");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("text topic message 2222");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void subscribe() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName,password,url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic-mode-3");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            if(message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("subscribe: " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // listen for loop
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }
}
