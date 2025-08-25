package com.cakapbot.api.adapters.out.inmemory;

import com.cakapbot.api.ports.ChatStore;
import com.cakapbot.api.domain.chat.ChatHistory;
import com.cakapbot.api.domain.chat.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryChatStore implements ChatStore {

    private final Map<String, ChatHistory> chatDataStore = new HashMap<>();


    public ChatHistory save(ChatHistory chatHistory) {
        chatDataStore.put(chatHistory.getSessionId(), chatHistory);
        return chatHistory;
    }

    //todo how would this be transaction... and resolve conflicts.
    public ChatHistory append(String sessionId, ChatMessage newMessage) {
        ChatHistory history = find(sessionId);
        history.getMessages().add(newMessage);
        return history;
    }

    public ChatHistory find(String sessionId) {
        return chatDataStore.get(sessionId);
    }
}
