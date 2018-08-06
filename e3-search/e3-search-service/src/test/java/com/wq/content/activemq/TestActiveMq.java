package com.wq.content.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TestActiveMq {


    public void testQueueProducer() throws Exception{

        /*
        第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
        第二步：使用ConnectionFactory对象创建一个Connection对象。
        第三步：开启连接，调用Connection对象的start方法。
        第四步：使用Connection对象创建一个Session对象。
        第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        第六步：使用Session对象创建一个Producer对象。
        第七步：创建一个Message对象，创建一个TextMessage对象。
        第八步：使用Producer对象发送消息。
        第九步：关闭资源。


        */
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.1.179:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue1");
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage("hello active myssss");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();

    }

    /*消费者：接收消息。
    第一步：创建一个ConnectionFactory对象。
    第二步：从ConnectionFactory对象中获得一个Connection对象。
    第三步：开启连接。调用Connection对象的start方法。
    第四步：使用Connection对象创建一个Session对象。
    第五步：使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
    第六步：使用Session对象创建一个Consumer对象。
    第七步：接收消息。
    第八步：打印消息。
    第九步：关闭资源*/
    public void testQueueConsumer() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.1.179:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue1");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    public void testTopicProducer() throws Exception{

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.1.179:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-queue");
        MessageProducer producer = session.createProducer(topic);
        TextMessage message = session.createTextMessage("hello active myssss this is topic");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();

    }


    public void testTopicConsumer() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.1.179:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-queue");
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("i'm wating");
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

}
