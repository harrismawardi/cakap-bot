package com.cakapbot.api.adapters.in.web;

import com.cakapbot.api.adapters.in.web.dto.LessonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController {

    @GetMapping(path = "/api/v1/lessons")
    public ResponseEntity<LessonResponse> getLessons(
            @RequestParam("lang") String languageCode
    ) {
        return ResponseEntity.ok().build();
    }
}
