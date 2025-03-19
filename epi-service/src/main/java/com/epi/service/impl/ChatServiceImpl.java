package com.epi.service.impl;

import com.epi.repository.ChatRepository;
import com.epi.service.ChatService;
import com.epi.dto.ChatDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * 对话服务实现类
 * @author dai
 */
@Service
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private ChatClient chatClient;

    @Override
    public String chat(ChatDto chatDto) {
        chatRepository.addChatId(chatDto.getPrompt(), String.valueOf(chatDto.getId()));
        return chatClient
                .prompt(chatDto.getPrompt())
                .advisors(a -> {a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatDto.getId());})
                .toString();
    }
}
