package com.cakapbot.api.controller;

import com.cakapbot.api.model.chat.UserTurn;
import com.cakapbot.api.enums.LanguageCode;
import com.cakapbot.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(path = "/api/v1/chat/start")
    public ResponseEntity<ChatResponse> startLessonChat(
            @RequestParam(value = "lesson-id") String lessonId,
            @RequestParam(value = "lang") String language
    ) {
        log.info("New chat request received.");
        ChatResponse startMessage = chatService.startChat(lessonId, LanguageCode.fromString(language));
        return ResponseEntity.ok().body(startMessage);
    }


    @PostMapping(path = "/api/v1/chat/message")
    public ResponseEntity<ChatResponse> makeChatResponse(
        @RequestBody ChatRequest request
    ) {
        UserTurn userTurn = new UserTurn(request.getMessage(), Instant.now().getEpochSecond());
        return ResponseEntity.ok(chatService.reply(request.getSessionId(), userTurn));
    }
}
