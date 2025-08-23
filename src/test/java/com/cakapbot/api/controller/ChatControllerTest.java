package com.cakapbot.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenStartChatRequest_whenNoLessonId_return400() throws Exception {
        mockMvc.perform(get("/api/v1/chat/start"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("lesson-id is required"));
    }
}
