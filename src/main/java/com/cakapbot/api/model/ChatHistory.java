package com.cakapbot.api.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatHistory {

    //todo should we distinguish between ai and user messages?

    //todo can this be a record?
    String sessionId;
    long timeInitiated;
    List<ChatMessage> messages;

    public ChatHistory(String sessionId) {
        this.sessionId = sessionId;
        this.timeInitiated = Instant.now().getEpochSecond();
        this.messages = new ArrayList<>();
    }
}
