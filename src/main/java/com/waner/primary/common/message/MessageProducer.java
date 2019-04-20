package com.waner.primary.common.message;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component("producer")
public class MessageProducer {

    private final JmsMessagingTemplate jmsMessagingTemplate;

    public MessageProducer(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * @param destination 目标
     * @param mailMessage 存储在队列中的发送消息
     */
    public void sendMessage(Destination destination, final MailMessage mailMessage) {
        jmsMessagingTemplate.convertAndSend(destination, mailMessage);
    }
}
