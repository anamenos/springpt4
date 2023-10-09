package com.anild.springpt4.controller;

import com.anild.springpt4.model.SearchRequest;
import com.anild.springpt4.service.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ChatGPTRestController {

    private ChatGPTService chatGPTService;

    public ChatGPTRestController(ChatGPTService service){
        this.chatGPTService = service;
    }

    @PostMapping("/searchChatGPT")
    public String searchChatGPT(@RequestBody SearchRequest searchRequest) throws IOException {

        return chatGPTService.processSearch(searchRequest.getQuery());
    }
}
