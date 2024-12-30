package com.mm.webstn.services;

import com.mm.webstn.domain.HistoryMessage;

import java.util.List;

import com.mm.webstn.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageConsumerServiceImpl  implements MessageConsumerService{

    private final Logger logger= LoggerFactory.getLogger(MessageConsumerServiceImpl.class);
    @Autowired
    private MessageRepository messageRepository;

    public MessageConsumerServiceImpl(MessageRepository messageRepository) {
        super();
        this.messageRepository = messageRepository;
    }

    @Override
    public List<HistoryMessage> getAllHistoryMessages() {
        return messageRepository.findAll();
    }

    @Override
    public HistoryMessage saveHistoryMessage(HistoryMessage historyMessage){
        logger.info("About to save HM: "+ historyMessage.getMessage());
        return messageRepository.save(historyMessage);
    }

}
