package com.cakapbot.api.adapters.out.inmemory;

import com.cakapbot.api.exception.NotFoundException;
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
    private static final String GREETING_CHAT_CONTEXT = """
        You have been introduced to a mutual friend. Get to know them through basic questions. Topics to talk about over conversation:
        Well being, location, age, preferred name, hobbies, job.
    """;

    @PostConstruct
    private void populateData() {
        lessonDataStore.put(1L, new Lesson("greetings",
                "ms-MY",
                "Greetings and Introductions",
                "Apa Kabar? Selemat pagi",
                "How are you? Good Morning",
                GREETING_CHAT_CONTEXT
        ));
        lessonDataStore.put(2L, new Lesson(
                "greetings",
                "ar-SA",
                "Greetings and Introductions",
                "السلام عليكم، صباح الخير",
                "As-salāmu ʿalaykum, ṣabāḥ al-khayr",
                GREETING_CHAT_CONTEXT
        ));
        lessonDataStore.put(3L, new Lesson(
                "greetings",
                "fr-FR",
                "Greetings and Introductions",
                "ca va?",
                "how is it going?",
                GREETING_CHAT_CONTEXT
        ));
    }


    public Lesson find(String lessonSlug, LanguageCode languageCode) {
        Set<Map.Entry<Long, Lesson>> lessons = lessonDataStore.entrySet();

        return lessons.stream()
                .filter(entry -> Objects.equals(entry.getValue().lessonSlug(), lessonSlug)
                        && Objects.equals(entry.getValue().languageCode(), languageCode.toString()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lesson is not found."))
                .getValue();
    }
}
