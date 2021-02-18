package com.framework.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * queue mould point to point
 * no depend to time, product and consumer do not have a relationship.
 * only the product sent a message and consumer could receive a message.
 * one message only consumer once
 */
public class DemoQueue {

    private String userName = "neo";
    private String password = "admin";
    private String url = "tcp://192.168.1.25:61616";

    @Test
    public void send() throws JMSException {
        // 1 create connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName,password,url);
        // 2 use connection factory to create a connection
        Connection connection = connectionFactory.createConnection();
        // 3 start connection
        connection.start();
        // 4 use connection object to create a session communication object
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5 use session to create a queue object (contain queue and topic) one to one, one to many
        Queue queue = session.createQueue("queue-mode-test2");
        // 6 create a producer by session
        MessageProducer producer = session.createProducer(queue);
        // 7 create a text message by session
        TextMessage textMessage = session.createTextMessage("999");
        // 8 send message
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }


    // multiple receive only on receive consumer could receive a queue from product like nginx
    @Test
    public void receive() throws JMSException, IOException {
        // 1 create a connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, url);
        // 2 create a connection obj by a connection factory
        Connection connection  = connectionFactory.createConnection();
        // 3 start connection
        connection.start();
        // 4 create session by connection
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5 create queue, keep same queue for send method
        Queue queue = session.createQueue("queue-mode-test2");
        // 6 create message consumer by session
        MessageConsumer messageConsumer = session.createConsumer(queue);
        // 7 set message object to message consumer
        messageConsumer.setMessageListener(message -> {
            if(message instanceof  TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("receive message: " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // wait the producer sent message
        System.in.read();
        // 8 shut off resource
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
