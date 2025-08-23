package com.cakapbot.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ChatMessage {
    @NonNull
    String message;
    @NonNull
    String englishTranslation;
    @NonNull
    String sessionId;
    String[] hints;
}
