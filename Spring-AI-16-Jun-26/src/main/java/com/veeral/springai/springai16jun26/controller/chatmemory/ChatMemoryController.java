package com.veeral.springai.springai16jun26.controller.chatmemory;

import com.veeral.springai.springai16jun26.dto.comm.reqres.PromptResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatmemory")
public class ChatMemoryController {

    private final ChatClient googleChatClient;
    private final ChatClient groqChatClient;

    public ChatMemoryController(
            @Qualifier("googleChatClientWithChatMemory") ChatClient googleChatClient,
            @Qualifier("groqChatClientWithChatMemory") ChatClient groqChatClient) {

        this.googleChatClient = googleChatClient;
        this.groqChatClient = groqChatClient;
    }

    @GetMapping("/go")
    public ResponseEntity<PromptResponse> testChatMemoryGoogle(@RequestParam(defaultValue = "Tell me a joke about Java") String prompt, @RequestParam(name = "conid", defaultValue = "default") String conId) {
        String content = googleChatClient.prompt().user(prompt).advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conId)).call().content();
        PromptResponse response = new PromptResponse(content, "Google (Gemini)", HttpStatus.OK.value());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/gr")
    public ResponseEntity<PromptResponse> testChatMemoryGroq(@RequestParam(defaultValue = "Tell me a joke about Python") String prompt, @RequestParam(name = "conid", defaultValue = "default") String conId) {
        String content = groqChatClient.prompt().user(prompt).advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conId)).call().content();
        PromptResponse response = new PromptResponse(content, "Groq (OpenAI)", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}