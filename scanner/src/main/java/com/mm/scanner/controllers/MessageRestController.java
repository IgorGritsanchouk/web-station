package com.mm.scanner.controllers;

import com.mm.scanner.component.MessageProducer;
import com.mm.shared.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestController {

    //private final MessageService messageService;
    //@Autowired
    private final MessageProducer messageProducer;

    public MessageRestController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/scanner/publish-message")
    public ResponseEntity<String> publishMessage(@RequestBody String messageText) {

        Message message = new Message(messageText);
        try {
            messageProducer.sendMessage(message);
            return new ResponseEntity<>("Message published successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error publishing message. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
