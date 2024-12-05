package com.mm.webstn.component;

import com.mm.shared.domain.Message;
import com.mm.webstn.domain.HistoryMessage;
import com.mm.webstn.services.MessageConsumerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class MessageConsumer {

    @Autowired
    MessageConsumerService messageConsumerService;

    private final String QUEUE = "scanner.queue";

    @Getter
    private ArrayList<String> receivedMessages= new ArrayList();

    @JmsListener(destination = QUEUE)
    public void receiveMessage(Message message) {
       System.out.println("Received message: "+message.getText());
        receivedMessages.add(message.getText());

        Date date = new Date(System.currentTimeMillis());
        HistoryMessage historyMessage = new HistoryMessage();
        historyMessage.setDate(date.toInstant());
        historyMessage.setMessage(message.getText());
        messageConsumerService.saveHistoryMessage(historyMessage);
    }

}
