package com.cakapbot.api.data;

import com.cakapbot.api.model.ChatHistory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ChatDataStore {

    private final Map<Long, ChatHistory> chatDataStore = new HashMap<>();


    public void postChat(ChatHistory chatHistory) {
        chatDataStore.put((long) UUID.randomUUID().hashCode(), chatHistory);
    }

    public ChatHistory getChatBySessionId(String sessionId) {
        return chatDataStore.entrySet().stream()
                .filter(entry -> entry.getValue().getSessionId().equals(sessionId))
                .toList().getFirst().getValue();
    }
}
