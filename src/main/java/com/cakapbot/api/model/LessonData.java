package com.cakapbot.api.model;

public record LessonData(
    long id,
    String lessonSlug,
    String languageCode,
    String startMessage,
    String translation,
    String llmSystemPrompt
) {};

