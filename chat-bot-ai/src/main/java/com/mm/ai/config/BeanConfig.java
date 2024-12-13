package com.mm.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    private final Logger logger = LoggerFactory.getLogger(BeanConfig.class);

    @Bean
    public ChatClient openAIChatClient(OpenAiChatModel chatModel) {

        logger.info("OpenAIChatClient / MessageChatMemoryAdvisor - model: " + chatModel.getDefaultOptions().getModel());
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        ChatClient chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        logger.info("OpenAI chat client initialized ok. ");

        return chatClient;
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {

        logger.info("OllamaChatClient / MessageChatMemoryAdvisor - model: " + chatModel.getDefaultOptions().getModel());
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        ChatClient chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        logger.info("OllamaChatClient chat client initialized ok. ");

        return chatClient;
    }

    //    @Bean
//    public ChatClient regOpenAIChatClient(OpenAiChatModel chatModel, VectorStore vectorStore) {
//
//        logger.info("RegOpenAIChatClient / QuestionAnswerAdvisor - model: " + chatModel.getDefaultOptions().getModel());
//        ChatClient.Builder builder = ChatClient.builder(chatModel);
//        ChatClient chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
//                .build();
//        logger.info("RegOpenAIChatClient initialized ok: " + chatClient.toString());
//
//        return chatClient;
//    }



//    @Bean
//    public ChatClient regOllamaChatClient(OllamaChatModel chatModel, VectorStore vectorStore) {
//
//        logger.info("RegOllamaChatClient / QuestionAnswerAdvisor model: " + chatModel.getDefaultOptions().getModel());
//
//        ChatClient.Builder builder = ChatClient.builder(chatModel);
//        ChatClient chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
//                .build();
//        logger.info("RegOllamaChatClient initialized ok: " + chatClient.toString());
//
//        return chatClient;
//    }

}
