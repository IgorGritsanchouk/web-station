package com.mm.ai.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@Controller
@CrossOrigin
public class OllamaChatController {

    private static final Logger log= LoggerFactory.getLogger(OllamaChatController.class);

    private final ChatClient chatClient;

    // removed ollama chat client/ use open ai instead
    //public OllamaChatController(@Qualifier("ollamaChatClient") ChatClient chatClient) {
    //    this.chatClient = chatClient;
    //}
    public OllamaChatController(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

//    @PostMapping("/chat")
//    public String chat(@RequestParam String message) {
//        log.info("localhost:88/chat?message= " + message);
//        return chatClient.prompt()
//                .user(message)
//                .call()
//                .content();
//    }

//    @GetMapping("/api-ollama-stream")
//    public Flux<String> streamOllama(@RequestParam String question) {
//        // references index.html in static folder. root : localhost:88
//        log.info("localhost:88/api-ollama-stream?question= " + question);
//        Flux<String> content= chatClient.prompt()
//                .user(question)
//                .stream()
//                .content();
//
//        log.info("localhost:88/api-ollama-stream  content: "+ content);
//
//        return content;
//    }

    @GetMapping("/ollama-home")
    public String ollamaHome() {
        log.info("ROOT for Chat Gpt clone -  http://localhost:88/ollama-home" );
        return "ollama-index";
    }

    @HxRequest
    @PostMapping("/ollama-chat-client/chat")
    public HtmxResponse ollamaGenerate(@RequestParam String message, Model model) {
        log.info("User message: " + message);
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();
        model.addAttribute("response", response);
        model.addAttribute("message", message);

        return HtmxResponse.builder()
                .view("response :: responseFragment")
                .view("recent-message-list :: messageFragment")
                .build();
    }
}
