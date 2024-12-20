package com.mm.ai;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = {OllamaAutoConfiguration.class})
//@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class})
public class ChatBotAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBotAiApplication.class, args);
    }

}
