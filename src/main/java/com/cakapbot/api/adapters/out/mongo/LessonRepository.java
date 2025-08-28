//package com.cakapbot.api.adapters.out.mongo;
//
//import com.cakapbot.api.adapters.out.mongo.doc.LessonDocument;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.Optional;
//
//public interface LessonRepository extends MongoRepository<LessonDocument, String> {
//
//    Optional<LessonDocument> findFirstByLessonSlugAndLanguageCodeAndStatusOrderByVersionDesc(String slug, String lang, String status);
//
//}
