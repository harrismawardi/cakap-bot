package com.cakapbot.api.data;

import com.cakapbot.api.model.LessonData;
import com.cakapbot.api.model.enums.LanguageCode;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
public class LessonDataStore {

    private final Map<Long, LessonData> lessonDataStore = new HashMap<>();
    private final String SYSTEM_PROMPT = """
        You are an ai assisted language learning chat-bot. You should give a reply in bahasa malay for the given response. keep answers short.
    """;

    @PostConstruct
    private void populateData() {
        lessonDataStore.put(1L, new LessonData(1, "greetings", "ms-MY", "Apa Kabar? Selemat pagi", "How are you? Good Morning", SYSTEM_PROMPT));
    }


    public LessonData getLessonData(String lessonSlug, LanguageCode languageCode) {
        Set<Map.Entry<Long, LessonData>> lessons = lessonDataStore.entrySet();
        return lessons.stream().filter(entry -> Objects.equals(entry.getValue().lessonSlug(), lessonSlug) &&
                    Objects.equals(entry.getValue().languageCode(), languageCode.toString())
        ).findFirst().get().getValue(); //todo cleanup
    }
}
