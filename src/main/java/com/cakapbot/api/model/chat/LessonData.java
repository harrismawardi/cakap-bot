package com.cakapbot.api.model.chat;

public record LessonData(
    String lessonSlug,
    String languageCode,
    String startMessage,
    String translation,
    String llmSystemPrompt
) {};

