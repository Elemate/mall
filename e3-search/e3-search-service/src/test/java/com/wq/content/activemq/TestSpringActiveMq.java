package com.wq.content.activemq;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class TestSpringActiveMq {


    public void testProduce() throws Exception{
        //初始化spring
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //获取bean
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Destination destination = (Destination) context.getBean("queueDestination");
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("哈哈，我很卡哇伊");
                return textMessage;
            }
        });

    }

    public void testConsumer() throws Exception{
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        System.in.read();
    }
}
