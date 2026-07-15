package com.veeral.springai.springai16jun26.controller.chp02;

import com.veeral.springai.springai16jun26.model.reqres.PromptRequest;
import com.veeral.springai.springai16jun26.model.reqres.PromptResponse;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/2")
public class AiController02 {

    private final ChatClient googleChatClient;
    private final ChatClient groqChatClient;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    public AiController02(
            @Qualifier("googleChatClientWithDefaultSystemAndUserMessage") ChatClient googleChatClient,
            @Qualifier("groqChatClientWithDefaultSystemAndUserMessage") ChatClient groqChatClient) {

        this.googleChatClient = googleChatClient;
        this.groqChatClient = groqChatClient;
    }

    @GetMapping("/go/joke")
    public String tellMeJokeGoogle(@RequestParam(defaultValue = "Tell me a joke about Java") String prompt) {
        return googleChatClient.prompt(prompt).system("You are a helpful HR assistant.").call().content();
    }

    @GetMapping("/gr/joke")
    public String tellMeJokeGroq(@RequestParam(defaultValue = "Tell me a joke about Python") String prompt) {
        return groqChatClient.prompt(prompt).system("You are a helpful HR assistant.").call().content();
    }


    @PostMapping("/go/sm")
    public ResponseEntity<PromptResponse> systemPromptGoogle(@Valid @RequestBody PromptRequest request) {
        String content = googleChatClient.prompt(request.getPrompt()).system("You are a helpful HR assistant. And will answer only HR related queries").call().content();
        PromptResponse response = new PromptResponse(content, "Google GenAI", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/gr/sm")
    public ResponseEntity<PromptResponse> systemPromptGroq(@Valid @RequestBody PromptRequest request) {
        String content = groqChatClient.prompt(request.getPrompt()).system("You are a helpful HR assistant. And will answer only HR related queries").call().content();
        PromptResponse response = new PromptResponse(content, "Groq (OpenAI)", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }



    @PostMapping("/go/sm/spt")
    public ResponseEntity<PromptResponse> systemPromptTemplateGoogle(@Valid @RequestBody PromptRequest request) {
        String content = googleChatClient.prompt(request.getPrompt()).system(systemPromptTemplate).call().content();
        PromptResponse response = new PromptResponse(content, "Google GenAI", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/gr/sm/upt")
    public String emailResponse(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage) {
        return groqChatClient
                .prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email
                        responses to improve the productivity of the customer support team
                        """)
                .user(promptTemplateSpec ->
                        promptTemplateSpec.text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call().content();
    }



}
