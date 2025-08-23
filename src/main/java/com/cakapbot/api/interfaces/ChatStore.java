package com.cakapbot.api.interfaces;

import com.cakapbot.api.model.chat.ChatHistory;
import com.cakapbot.api.model.chat.ChatMessage;


public interface ChatStore {
    ChatHistory save(ChatHistory chat);
    ChatHistory find(String sessionId);
    ChatHistory append(String sessionId, ChatMessage message);
}
