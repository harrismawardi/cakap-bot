package com.cakapbot.api.controller;

import lombok.Data;

@Data
public class ChatRequest {
    private String sessionId;
    private String message;
}
