package com.veeral.springai.springai16jun26chepter01.config.chp01;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMClientConfigurationConfig {

    //Chapter 01
    @Bean
    public ChatClient googleChatClient(@Qualifier("googleGenAiChatModel") ChatModel googleChatModel) {
        return ChatClient.builder(googleChatModel).build();
    }

    @Bean
    public ChatClient groqChatClient(@Qualifier("openAiChatModel") ChatModel groqChatModel) {
        return ChatClient.builder(groqChatModel).build();
    }

    //Chapter 02
    @Bean("googleChatClientWithDefaultSystemAndUserMessage")
    public ChatClient googleChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("googleGenAiChatModel") ChatModel googleChatModel) {
        return ChatClient.builder(googleChatModel).build();
    }

    @Bean("groqChatClientWithDefaultSystemAndUserMessage")
    public ChatClient groqChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("openAiChatModel") ChatModel groqChatModel) {
        return ChatClient.builder(groqChatModel).build();
    }

}
