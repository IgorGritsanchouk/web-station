package com.mm.webstn.bootstrap;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.mm.webstn.domain.HistoryMessage;
import com.mm.webstn.repository.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

    private final MessageRepository messageRepository;

    public BootStrapData(MessageRepository messageRepository) {

        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("... START Bootstrap data ...");

        long now = System.currentTimeMillis();
        Date date = new Date(now);

       //Timestamp ts = new Timestamp(System.currentTimeMillis());
        HistoryMessage message1 = new HistoryMessage();
        //LocalDateTime localDateTime = LocalDateTime.now();
        message1.setDate(date.toInstant());
        message1.setMessage("Fst Message to be saved.");
        messageRepository.save(message1);

        System.out.println("...http://localhost:81/scanner/message/new...");
        System.out.println("... END Boot strap data ...");
    }
}