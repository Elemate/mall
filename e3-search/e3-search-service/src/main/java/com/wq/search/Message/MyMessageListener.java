package com.wq.search.Message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
    用来接受消息之后进行业务逻辑处理
*/

public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
