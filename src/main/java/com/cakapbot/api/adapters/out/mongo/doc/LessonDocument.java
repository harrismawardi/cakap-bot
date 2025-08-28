//package com.cakapbot.api.adapters.out.mongo.doc;
//
////import org.springframework.data.annotation.Id;
////import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.Map;
//
//@Document("lessonVariants")
//public record LessonDocument(
//        @Id String id,
//        String lessonSlug,
//        String languageCode,
//        String version,
//        String status,
//        String title,
//        String startMessage,
//        String translation,
//        String lessonScope,
//        Map<String, Object> guidelines,
//        List<Map<String, String>> examples,
//        Instant createdAt,
//        Instant updatedAt
//) {}