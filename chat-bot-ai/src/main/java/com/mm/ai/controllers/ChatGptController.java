package com.mm.ai.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@CrossOrigin
public class ChatGptController {

    private static final Logger log = Logger.getLogger(ChatGptController.class.getName());
    private final ChatClient chatClient;

    public ChatGptController(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/open-ai-home")
    public String home() {
        log.info("ROOT for Chat Gpt clone -  http://localhost:88/open-ai-home" );
        return "openai-index";
    }

    @HxRequest
    @PostMapping("/chat-client/chat")
    public HtmxResponse generate(@RequestParam String message, Model model) {
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
