package com.epi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI配置
 * @author dai
 */

@Configuration
@Slf4j
public class ChatConfig {

    /**
     * 对话记忆Bean
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * 聊天客户端Bean
     * @param model
     * @param memory
     * @return
     */
    @Bean
    public ChatClient chatClient(OllamaChatModel model, ChatMemory memory){
        return ChatClient.builder(model)
                .defaultSystem("你是一个疫情助手, 你可以分析疫情数据，推测疫情发展情况")
                .defaultAdvisors(new SimpleLoggerAdvisor()) // 添加默认的Advisor,记录日志
                .defaultAdvisors(new MessageChatMemoryAdvisor(memory))
                .build(); // 构建ChatClient实例
    }
}
