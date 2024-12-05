package com.mm.webstn.controllers;

import com.mm.webstn.component.MessageConsumer;
import com.mm.webstn.domain.HistoryMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MessageConsumerController {

    private final MessageConsumer messageConsumer;

    public MessageConsumerController(MessageConsumer messageConsumer) {
        super();
        this.messageConsumer = messageConsumer;
    }

    @GetMapping("/webstn/received-messages")
    public String receivedMessagesPage(Model model) {

        ArrayList<HistoryMessage> messages= new ArrayList();
        ArrayList<String> received= messageConsumer.getReceivedMessages();

        for(int i=0; i<received.size(); i++){
            HistoryMessage historyMessage = new HistoryMessage();
            historyMessage.setId(Long.valueOf(i+1));
            historyMessage.setMessage(received.get(i));
            messages.add(historyMessage);
            model.addAttribute("messages", messages);
        }

        return "received_messages_page";
    }

}
