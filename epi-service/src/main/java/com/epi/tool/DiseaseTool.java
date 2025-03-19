package com.epi.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DiseaseTool {

    @Tool(name = "查询疾病", description = "根据用户的症状返回相关的疾病")
    public List<String> queryDisease(List<String> symptoms){
        if (symptoms.size() <= 1){
            return List.of("请提供更多症状以提高诊断准确的");
        }

        return List.of("根据用户的症状返回相关的疾病");
    }
}
