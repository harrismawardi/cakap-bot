package com.cakapbot.api.service;

import com.cakapbot.api.client.LlmClient;
import com.cakapbot.api.data.ChatDataStore;
import com.cakapbot.api.data.LessonDataStore;
import com.cakapbot.api.exception.NotImplementedException;
import com.cakapbot.api.interfaces.Chat;
import com.cakapbot.api.model.ChatHistory;
import com.cakapbot.api.model.ChatMessage;
import com.cakapbot.api.model.LessonData;
import com.cakapbot.api.model.enums.LanguageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService implements Chat {

    private final LlmClient llmClient;
    private final LessonDataStore lessonDataStore;
    private final ChatDataStore chatDataStore;

    public ChatMessage startChat(String lessonSlug, LanguageCode languageCode) {
        LessonData lesson = lessonDataStore.getLessonData(lessonSlug, languageCode);
        String sessionId = UUID.randomUUID().toString();
        chatDataStore.postChat(new ChatHistory(sessionId));
        return ChatMessage.builder()
                .sessionId(sessionId)
                .message(lesson.startMessage())
                .englishTranslation(lesson.translation())
                .hints(new String[]{})
                .build();
    }

    public ChatMessage reply(ChatMessage receivedMessage) {
        // takes received message and gets response from LLM
            //if


        throw new NotImplementedException("Reply not implemented yet");
    }

    public ChatMessage endChat() {
        throw new NotImplementedException("endChat not implemented yet");
    }

}
