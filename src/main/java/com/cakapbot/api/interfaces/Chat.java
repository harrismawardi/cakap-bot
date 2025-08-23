package com.cakapbot.api.interfaces;

import com.cakapbot.api.controller.ChatResponse;
import com.cakapbot.api.model.chat.UserTurn;
import com.cakapbot.api.enums.LanguageCode;

public interface Chat {
    ChatResponse startChat(String lessonSlug, LanguageCode languageCode);
    ChatResponse reply(String sessionId, UserTurn message);
}
