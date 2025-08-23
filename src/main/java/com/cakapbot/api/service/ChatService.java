package com.cakapbot.api.service;

import com.cakapbot.api.data.LessonDataStore;
import com.cakapbot.api.interfaces.Chat;
import com.cakapbot.api.interfaces.ChatStore;
import com.cakapbot.api.controller.ChatResponse;
import com.cakapbot.api.enums.LanguageCode;
import com.cakapbot.api.model.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.cakapbot.api.enums.ChatRole.BOT;
import static com.cakapbot.api.enums.ChatRole.USER;

@Service
@RequiredArgsConstructor
public class ChatService implements Chat {

    private final AiReplyService botReplyService;
    private final LessonDataStore lessonDataStore;
    private final ChatStore chatDataStore;

    public ChatResponse startChat(String lessonSlug, LanguageCode languageCode) {
        LessonData lesson = lessonDataStore.getLessonData(lessonSlug, languageCode);

        ChatMessage startMessage = new ChatMessage(BOT, lesson.startMessage(), Instant.now().getEpochSecond());
        ChatHistory newChat = ChatHistory.create(startMessage, lesson.llmSystemPrompt());
        chatDataStore.save(newChat);

        return ChatResponse.builder()
                .sessionId(newChat.getSessionId())
                .message(startMessage.message())
                .translation(lesson.translation())
                .build();
    }

    public ChatResponse reply(String sessionId, UserTurn userTurn) {
        ChatHistory history = chatDataStore.find(sessionId);
        BotTurn botTurn = botReplyService.generateReply(userTurn, history);

        chatDataStore.append(sessionId, new ChatMessage(USER, userTurn.message(), userTurn.timestamp()));
        chatDataStore.append(sessionId, new ChatMessage(BOT, botTurn.message(), botTurn.timestamp()));

        return ChatResponse.builder()
                .sessionId(sessionId)
                .message(botTurn.message())
                .translation(botTurn.translation())
                .hints(botTurn.hints())
                .build();
    }
}
