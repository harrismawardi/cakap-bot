package com.cakapbot.api.domain.lesson;

public record Lesson(
        String lessonSlug,
        String languageCode,
        String title,
        String startMessage,
        String translation,
        String lessonScope
) {}

