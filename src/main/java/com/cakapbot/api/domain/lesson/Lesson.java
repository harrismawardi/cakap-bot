package com.cakapbot.api.domain.lesson;

import java.util.List;
import java.util.Map;

public record Lesson(
        String lessonSlug,
        String languageCode,
        String title,
        String startMessage,
        String translation,
        String systemPrompt,
        Map<String, Object> guidelines,
        List<Map<String, String>> examples
) {};

