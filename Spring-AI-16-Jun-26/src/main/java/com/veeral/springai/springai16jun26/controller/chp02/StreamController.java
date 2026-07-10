package com.veeral.springai.springai16jun26.controller.chp02;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/2")
public class StreamController {

    private final ChatClient groqChatClient;

    public StreamController(@Qualifier("groqChatClientWithDefaultSystemAndUserMessage") ChatClient groqChatClient) {
        this.groqChatClient = groqChatClient;
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam("message") String message) {
        return groqChatClient.prompt().user(message).stream().content();
    }
}
