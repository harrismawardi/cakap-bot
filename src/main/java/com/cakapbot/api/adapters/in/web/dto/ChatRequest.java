package com.cakapbot.api.adapters.in.web.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String sessionId;
    private String message;
}
