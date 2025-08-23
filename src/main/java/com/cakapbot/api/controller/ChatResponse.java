package com.cakapbot.api.controller;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatResponse {
    String sessionId;
    String message;
    String translation;
    List<String> hints;
}
