package com.cakapbot.api.adapters.out.inmemory;

import com.cakapbot.api.ports.LessonStore;
import com.cakapbot.api.domain.lesson.Lesson;
import com.cakapbot.api.domain.lesson.LanguageCode;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
public class InMemoryLessonDataStore implements LessonStore {

    private final Map<Long, Lesson> lessonDataStore = new HashMap<>();
    private final String SYSTEM_PROMPT = """
        You speak Bahasa Malayu. Do not use religious phrases - stick to secular. For this conversation stay focused on this topic:
    """;

    @PostConstruct
    private void populateData() {
        lessonDataStore.put(1L, new Lesson("greetings", "ms-MY", "Apa Kabar? Selemat pagi", "How are you? Good Morning", SYSTEM_PROMPT + "greetings"));
    }


    public Lesson find(String lessonSlug, LanguageCode languageCode) {
        Set<Map.Entry<Long, Lesson>> lessons = lessonDataStore.entrySet();
        return lessons.stream().filter(entry -> Objects.equals(entry.getValue().lessonSlug(), lessonSlug) &&
                    Objects.equals(entry.getValue().languageCode(), languageCode.toString())
        ).findFirst().get().getValue(); //todo cleanup
    }
}
