package com.mm.ai.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
public class OpenAiRestChatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public OpenAiRestChatController(VectorStore vectorStore, @Qualifier("regOpenAIChatClient") ChatClient chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    private static final String SYSTEM_PROMPT = """
            Your are helpful AI assistant who responds to queries primarily based on the documents section below.

            Documents:

            {documents}

            """;

    @PostMapping("/rag-openai-chat")
    public String ragOpenAiChat(@RequestParam String message) {

        logger.info("localhost:88/rag-openai-chat?message= " + message);

        List<Document> relatedDocuments = vectorStore.similaritySearch(message);
        String documents = relatedDocuments.stream().map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        logger.info("got the document, length:  " + documents.length());

        String content = this.chatClient
                .prompt()
                .system(s -> s.text(SYSTEM_PROMPT).params(Map.of("documents", documents)))
                .user(message)
                .call()
                .content();

        logger.info("received chat client content :  " + content);
        return content;

    }

    @GetMapping("/rag-openai-stream")
    public Flux<String> ragOpenAiStream(@RequestParam String message) {

        logger.info("localhost:88/rag-openai-stream?question= " + message);

        List<Document> relatedDocuments = vectorStore.similaritySearch(message);
        String documents = relatedDocuments.stream().map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        logger.info("got the document, length:  " + documents.length());

        Flux<String> content= this.chatClient
                .prompt()
                .system(s -> s.text(SYSTEM_PROMPT).params(Map.of("documents", documents)))
                .user(message)
                .stream()
                .content();

        logger.info("localhost:88/rag-openai-stream content: "+ content);

        return content;
    }

    @GetMapping("/get-openai-chat")
    public String getOpenAiChat(@RequestParam String question) {

        logger.info("localhost:88/get-openai-chat?question= " + question);
        String content= chatClient.prompt()
                .user(question)
                .call()
                .content();

        logger.info("localhost:88/get-openai-chat content: "+ content);

        return content;
    }

    @GetMapping("/get-openai-stream")
    public Flux<String> getOpenAiStream(@RequestParam String question) {

        logger.info("localhost:88/get-openai-stream?question= " + question);
        Flux<String> content= chatClient.prompt()
                .user(question)
                .stream()
                .content();

        logger.info("localhost:88/get-openai-stream content: "+ content);

        return content;
    }

}

