package com.epi.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 聊天记录
 * @author dai
 */
@Component
public class ChatRepositoryInMemory implements ChatRepository {

    private final Map<String, Set<String>> chatIds = new HashMap<>();

    @Override
    public void addChatId(String type, String chatId) {
        if (!chatIds.containsKey(chatId)){
            Set<String> ids = new HashSet<>();
            ids.add(chatId);
            chatIds.put(type, ids);
        }else {
            Set<String> ids = chatIds.get(type);
        }
    }

    @Override
    public Set<String> getChatIds(String type) {
        return Set.of();
    }
}
