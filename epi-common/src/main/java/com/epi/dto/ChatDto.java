package com.epi.dto;

import lombok.Data;

/**
 * 对话请求DTO
 * @author dai
 */
@Data
public class ChatDto {

    /**
     * 对话id
     */
    private Long id;

    /**
     * 对话内容
     */
    private String prompt;
}
