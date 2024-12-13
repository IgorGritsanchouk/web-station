package com.mm.ai.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OllamaRestController {

    private static final Logger log= LoggerFactory.getLogger(OllamaRestController.class);

    private final ChatClient chatClient;

    public OllamaRestController(@Qualifier("ollamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/ollama-rest-chat")
    public String ollamaRestChat(@RequestParam String message) {

        log.info("localhost:88/ollama-rest-chat?message= " + message);
        String content= chatClient.prompt()
                .user(message)
                .call()
                .content();
        log.info("ollama-rest-chat content: " + message);

        return content;
    }

    @GetMapping("/rest-ollama-stream")
    public Flux<String> restOllamaStream(@RequestParam String message) {
        // references index.html in static folder. root : localhost:88
        log.info("localhost:88/rest-ollama-stream?message= " + message);
        Flux<String> content= chatClient.prompt()
                .user(message)
                .stream()
                .content();

        log.info("localhost:88/rest-ollama-stream  content: "+ content);

        return content;
    }

    @GetMapping("/api-ollama")
    public String chatOllama(@RequestParam String question) {

        log.info("localhost:88/api-ollama?question= " + question);
        String content= chatClient.prompt()
                .user(question)
                .call()
                .content();

        log.info("localhost:88/api-ollama  content: "+ content);

        return content;
    }

    @GetMapping("/api-ollama-stream")
    public Flux<String> streamOllama(@RequestParam String question) {

        log.info("localhost:88/api-ollama-stream?question= " + question);
        Flux<String> content= chatClient.prompt()
                .user(question)
                .stream()
                .content();

        log.info("localhost:88/api-ollama-stream  content: "+ content);

        return content;
    }


    @GetMapping("/api-ollama/fact")
    public String fact() {
        log.info("localhost:88/api-ollama/fact  - Generate the names of 5 famous pirates.");

        String content= chatClient.prompt()
                .user("Generate the names of 5 famous pirates.")
                .call()
                .content();

        log.info("localhost:88/api-ollama/fact content: "+ content);

        return content;
    }
}
