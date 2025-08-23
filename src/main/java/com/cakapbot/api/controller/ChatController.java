package com.cakapbot.api.controller;

import com.cakapbot.api.model.ChatMessage;
import com.cakapbot.api.model.enums.LanguageCode;
import com.cakapbot.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(path = "/api/v1/chat/start")
    public ResponseEntity<ChatMessage> startLessonChat(
            @RequestParam(value = "lesson-id") String lessonId,
            @RequestParam(value = "lang") String language
    ) {
        ChatMessage startMessage = chatService.startChat(lessonId, LanguageCode.fromString(language));
        return ResponseEntity.ok().body(startMessage);
    }
}
