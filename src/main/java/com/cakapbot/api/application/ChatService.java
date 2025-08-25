package com.cakapbot.api.application;

import com.cakapbot.api.adapters.out.inmemory.InMemoryLessonDataStore;
import com.cakapbot.api.ports.Chat;
import com.cakapbot.api.ports.ChatStore;
import com.cakapbot.api.adapters.in.web.dto.ChatResponse;
import com.cakapbot.api.domain.lesson.LanguageCode;
import com.cakapbot.api.domain.chat.*;
import com.cakapbot.api.domain.lesson.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.cakapbot.api.domain.chat.ChatRole.BOT;
import static com.cakapbot.api.domain.chat.ChatRole.USER;

@Service
@RequiredArgsConstructor
public class ChatService implements Chat {

    private final AiReplyService botReplyService;
    private final InMemoryLessonDataStore lessonDataStore;
    private final ChatStore chatDataStore;

    public ChatResponse startChat(String lessonSlug, LanguageCode languageCode) {
        Lesson lesson = lessonDataStore.find(lessonSlug, languageCode);

        ChatMessage startMessage = new ChatMessage(BOT, lesson.startMessage(), Instant.now().getEpochSecond());
        ChatHistory newChat = ChatHistory.create(startMessage, lesson.systemPrompt());
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
