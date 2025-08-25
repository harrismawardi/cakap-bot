package com.cakapbot.api.ports;

import com.cakapbot.api.adapters.in.web.dto.ChatResponse;
import com.cakapbot.api.domain.chat.UserTurn;
import com.cakapbot.api.domain.lesson.LanguageCode;

public interface Chat {
    ChatResponse startChat(String lessonSlug, LanguageCode languageCode);
    ChatResponse reply(String sessionId, UserTurn message);
}
