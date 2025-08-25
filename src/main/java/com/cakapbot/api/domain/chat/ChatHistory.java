package com.cakapbot.api.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ChatHistory {
    private final String sessionId;
    private final List<ChatMessage> messages;
    private final String systemPrompt;
    private final long startedAt;

    public static ChatHistory create(ChatMessage startMessage, String systemPrompt) {
        String sessionId = UUID.randomUUID().toString();
        long startedAt = startMessage.timestamp();

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(startMessage);

        return new ChatHistory(sessionId, messages, systemPrompt, startedAt);
    }
}
