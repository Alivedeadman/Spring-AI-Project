package com.veeral.springai.springai16jun26.controller.chp01;

import com.veeral.springai.springai16jun26.model.reqres.PromptRequest;
import com.veeral.springai.springai16jun26.model.reqres.PromptResponse;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1")
public class AiController01 {

    private final ChatClient googleChatClient;
    private final ChatClient groqChatClient;

    public AiController01(
            @Qualifier("googleChatClient") ChatClient googleChatClient,
            @Qualifier("groqChatClient") ChatClient groqChatClient) {
        
        this.googleChatClient = googleChatClient;
        this.groqChatClient = groqChatClient;
    }

    @GetMapping("/go/joke")
    public String testGoogle(@RequestParam(defaultValue = "Tell me a joke about Java") String prompt) {
        return googleChatClient.prompt(prompt).call().content();
    }

    @GetMapping("/gr/joke")
    public String testGroq(@RequestParam(defaultValue = "Tell me a joke about Python") String prompt) {
        return groqChatClient.prompt(prompt).call().content();
    }

    /**
     * POST endpoint to send a prompt to Google GenAI
     * @param request PromptRequest containing the prompt
     * @return ResponseEntity with PromptResponse
     */
    @PostMapping("/go/post")
    public ResponseEntity<PromptResponse> askGoogle(@Valid @RequestBody PromptRequest request) {
        String content = googleChatClient.prompt(request.getPrompt()).call().content();
        PromptResponse response = new PromptResponse(content, "Google GenAI", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * POST endpoint to send a prompt to Groq/OpenAI
     * @param request PromptRequest containing the prompt
     * @return ResponseEntity with PromptResponse
     */
    @PostMapping("/gr/post")
    public ResponseEntity<PromptResponse> askGroq(@Valid @RequestBody PromptRequest request) {
        String content = groqChatClient.prompt(request.getPrompt()).call().content();
        PromptResponse response = new PromptResponse(content, "Groq (OpenAI)", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
