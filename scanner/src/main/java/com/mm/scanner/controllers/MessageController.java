package com.mm.scanner.controllers;

import com.mm.scanner.component.MessageProducer;
import com.mm.scanner.domain.HistoryMessage;
import com.mm.shared.domain.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class MessageController {

    private final MessageProducer messageProducer;

    private long messagesSentId;
    private long errorsId;

    public MessageController(MessageProducer messageProducer) {
        super();
        this.messageProducer = messageProducer;
        messagesSentId= 1;
        errorsId= 1;
    }

    private ArrayList<HistoryMessage> messages= new ArrayList();

    @GetMapping("/scanner/info")
    public String sendMessageInfo(Model model, HttpServletRequest httpServletRequest) {

        HistoryMessage historyMessage = new HistoryMessage();
        String receivedMessage= httpServletRequest.getParameter("message");
        if( receivedMessage != null && !receivedMessage.isEmpty() ) {
            historyMessage.setId(messagesSentId++);
            historyMessage.setMessage(receivedMessage);
            messages.add(historyMessage);
        }else{
            // do nothing
        }

        model.addAttribute("messages", messages);
        return "scanner_send_messages_page";
    }

    @GetMapping("/scanner/error")
    public String sendMessageError(Model model, HttpServletRequest httpServletRequest) {

        HistoryMessage historyMessage = new HistoryMessage();
        historyMessage.setId(errorsId++);
        ArrayList<HistoryMessage> messages= new ArrayList();

        String error= httpServletRequest.getParameter("message");
        if( error != null && !error.isEmpty() ) {
            historyMessage.setMessage(error);
            messages.add(historyMessage);
        }else{
            historyMessage.setMessage("NO Errors encountered. Upi..Upi..");
            messages.add(historyMessage);
        }
        model.addAttribute("messages", messages);
        return "scanner_error_page";
    }

    @GetMapping("/scanner/message/new")
    public String createHistoryMessageForm(Model model) {
        HistoryMessage historyMessage = new HistoryMessage();
        model.addAttribute("historyMessage", historyMessage);
        return "scanner_new_message_page";
    }

    @PostMapping("/scanner/forward")
    public String saveHistoryMessage(@ModelAttribute("historyMessage") HistoryMessage historyMessage) {
        System.out.println("/scanner/forward has hmv: "+historyMessage.getMessage());
        Message message = new Message(historyMessage.getMessage());
        try {
            messageProducer.sendMessage(message);
            return "redirect:/scanner/info?message="+historyMessage.getMessage();
            //return new Responsed successfully", HttpStatus.OK);
        } catch (Exception ex) {
            //return new ResponseEntity<>("Error publishing message. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return "redirect:/scanner/error?message=SCANNER ERROR: " + ex.getMessage();
        }
    }
}
