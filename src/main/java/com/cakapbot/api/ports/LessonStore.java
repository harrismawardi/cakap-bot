package com.cakapbot.api.ports;

import com.cakapbot.api.domain.lesson.LanguageCode;
import com.cakapbot.api.domain.lesson.Lesson;

public interface LessonStore {
//    Lesson save(Lesson lesson);
    Lesson find(String lessonSlug, LanguageCode languageCode);
}
