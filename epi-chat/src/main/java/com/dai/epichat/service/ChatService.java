package com.dai.epichat.service;

import com.epi.dto.ChatDto;

/**
 * 对话服务类
 * @author dai
 */
public interface ChatService {

    /**
     * AI对话
     * @param chatDto 请求参数
     * @return AI回复
     */
    public String chat(ChatDto chatDto);
}
