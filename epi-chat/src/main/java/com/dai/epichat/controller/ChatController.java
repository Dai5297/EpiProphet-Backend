package com.dai.epichat.controller;

import com.dai.epichat.repository.ChatRepository;
import com.dai.epichat.service.ChatService;
import com.epi.base.Result;
import com.epi.dto.ChatDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对话接口
 * @author dai
 */

@RestController
@RequestMapping("/ai")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    @PreAuthorize("hasAnyAuthority('ai:chat')")
    public Result<String> chat(@RequestBody ChatDto chatDto) {
        String response = chatService.chat(chatDto);
        return Result.success(response);
    }
}
