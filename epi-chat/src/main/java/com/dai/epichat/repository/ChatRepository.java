package com.dai.epichat.repository;

import java.util.Set;

/**
 * 聊天存储接口
 * @author dai
 */
public interface ChatRepository {
    void addChatId(String type, String chatId);

    Set<String> getChatIds(String type);
}