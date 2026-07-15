package com.veeral.springai.springai16jun26.config.chatmemory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryClientConfig {


    //ChatMemory will have autoconfigured MessageWindowChatMemory
    @Bean("googleChatClientWithChatMemory")
    public ChatClient googleChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("googleGenAiChatModel") ChatModel googleChatModel, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return ChatClient.builder(googleChatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).build();
    }

    //ChatMemory will have autoconfigured MessageWindowChatMemory
    @Bean("groqChatClientWithChatMemory")
    public ChatClient groqChatClientWithDefaultSystemAndUserMessageClient(@Qualifier("openAiChatModel") ChatModel groqChatModel, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return ChatClient.builder(groqChatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).build();
    }

}
