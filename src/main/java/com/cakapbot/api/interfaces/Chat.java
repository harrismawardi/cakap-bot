package com.cakapbot.api.interfaces;

import com.cakapbot.api.model.ChatMessage;
import com.cakapbot.api.model.enums.LanguageCode;

public interface Chat {
    ChatMessage startChat(String lessonSlug, LanguageCode languageCode);
    ChatMessage reply(ChatMessage receivedMessage);
    ChatMessage endChat();
}
