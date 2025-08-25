package com.cakapbot.api.ports;

import com.cakapbot.api.domain.chat.ChatHistory;
import com.cakapbot.api.domain.chat.ChatMessage;


public interface ChatStore {
    ChatHistory save(ChatHistory chat);
    ChatHistory find(String sessionId);
    ChatHistory append(String sessionId, ChatMessage message);
}
