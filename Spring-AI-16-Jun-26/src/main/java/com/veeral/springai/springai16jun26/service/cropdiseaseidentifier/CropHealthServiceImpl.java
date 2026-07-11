package com.veeral.springai.springai16jun26.service.cropdiseaseidentifier;

import com.veeral.springai.springai16jun26.dto.cropdiseaseidentifier.AiProviderResult;
import com.veeral.springai.springai16jun26.dto.cropdiseaseidentifier.CropHealthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CropHealthServiceImpl implements CropHealthService {

    private static final Logger log = LoggerFactory.getLogger(CropHealthServiceImpl.class);

    private final ChatClient googleChatClient;
    private final ChatClient groqChatClient;

    public CropHealthServiceImpl(
            @Qualifier("googleChatClientWithDefaultSystemAndUserMessage") ChatClient googleChatClient,
            @Qualifier("groqChatClientWithDefaultSystemAndUserMessage") ChatClient groqChatClient) {
        this.googleChatClient = googleChatClient;
        this.groqChatClient = groqChatClient;
    }

    @Override
    public CropHealthResponse analyzeCropHealth(String description, List<MultipartFile> images) {
        List<Media> mediaList = new ArrayList<>();

        if (images != null) {
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    try {
                        mediaList.add(new Media(
                                MimeTypeUtils.parseMimeType(file.getContentType()),
                                new ByteArrayResource(file.getBytes())
                        ));
                    } catch (IOException e) {
                        log.error("Failed to read image file", e);
                        throw new RuntimeException("Failed to process uploaded image: " + e.getMessage());
                    }
                }
            }
        }

        String basePrompt = "You are an expert crop health analyzer. Analyze the provided image(s) and description to identify potential crop diseases.\n" +
                "User Description: " + description;

        CompletableFuture<AiProviderResult> googleFuture = CompletableFuture.supplyAsync(() ->
                callAiProvider(googleChatClient, "Google GenAI", basePrompt, mediaList));

        CompletableFuture<AiProviderResult> groqFuture = CompletableFuture.supplyAsync(() ->
                callAiProvider(groqChatClient, "Groq", basePrompt, mediaList));

        // Wait for both to complete
        CompletableFuture.allOf(googleFuture, groqFuture).join();

        List<AiProviderResult> results = new ArrayList<>();
        try {
            results.add(googleFuture.get());
        } catch (Exception e) {
            log.error("Google GenAI call failed", e);
            results.add(new AiProviderResult("Google GenAI", "Analysis Failed", 0, "Error: " + e.getMessage()));
        }

        try {
            results.add(groqFuture.get());
        } catch (Exception e) {
            log.error("Groq call failed", e);
            results.add(new AiProviderResult("Groq", "Analysis Failed", 0, "Error: " + e.getMessage()));
        }

        return new CropHealthResponse(results);
    }

    private AiProviderResult callAiProvider(ChatClient chatClient, String providerName, String promptText, List<Media> mediaList) {
        BeanOutputConverter<AiProviderResult> converter = new BeanOutputConverter<>(AiProviderResult.class);

        String format = converter.getFormat();
        String fullPrompt = promptText + "\n\n" + format + "\n\nMake sure to provide the providerName as '" + providerName + "'.";

        UserMessage userMessage = UserMessage.builder().text(fullPrompt).media(mediaList).build();

        String responseContent = chatClient.prompt()
                .messages(userMessage)
                .call()
                .content();

        return converter.convert(responseContent);
    }
}
