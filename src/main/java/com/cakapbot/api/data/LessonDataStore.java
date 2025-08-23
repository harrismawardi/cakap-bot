package com.cakapbot.api.data;

import com.cakapbot.api.model.chat.LessonData;
import com.cakapbot.api.enums.LanguageCode;
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
        You speak Bahasa Malayu. Do not use religious phrases - stick to secular. For this conversation stay focused on this topic:
    """;

    @PostConstruct
    private void populateData() {
        lessonDataStore.put(1L, new LessonData("greetings", "ms-MY", "Apa Kabar? Selemat pagi", "How are you? Good Morning", SYSTEM_PROMPT + "greetings"));
    }


    public LessonData getLessonData(String lessonSlug, LanguageCode languageCode) {
        Set<Map.Entry<Long, LessonData>> lessons = lessonDataStore.entrySet();
        return lessons.stream().filter(entry -> Objects.equals(entry.getValue().lessonSlug(), lessonSlug) &&
                    Objects.equals(entry.getValue().languageCode(), languageCode.toString())
        ).findFirst().get().getValue(); //todo cleanup
    }
}
