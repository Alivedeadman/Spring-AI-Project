package com.veeral.springai.springai16jun26.config.chp02;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMClientConfigurationConfig02 {

    @Bean("googleChatClientWithDefaultSystemAndUserMessage")
    public ChatClient googleChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("googleGenAiChatModel") ChatModel googleChatModel) {
        return ChatClient.builder(googleChatModel).defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @Bean("groqChatClientWithDefaultSystemAndUserMessage")
    public ChatClient groqChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("openAiChatModel") ChatModel groqChatModel) {
        return ChatClient.builder(groqChatModel).defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

}
