package com.mm.scanner.component;

import com.mm.shared.domain.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final String QUEUE= "scanner.queue";
    private final JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(Message message) {
        System.out.println("Producer message :" + message.getText());
        jmsTemplate.convertAndSend(QUEUE, message);
    }
}
