package com.cakapbot.api.application;

import com.cakapbot.api.domain.ai.PromptSpec;
import com.cakapbot.api.domain.chat.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PromptSpecComposer {

    private static final int MAX_MESSAGE_HISTORY_SENT = 10;
    private static final String GENERAL_SYSTEM_PROMPT = """
            You are replying to a chat. If I send a message in english, ask me to repeat in the language you understand.
             If there are small typos in my message -ignore them.
            The reply must be in Json to match the provided example. Keep messages short - like texting.
            """;

    public PromptSpec compose(String userPrompt, List<ChatMessage> previousMessages, String lessonPrompt) {
        String messages;
        try {
            previousMessages.sort(Comparator.comparingLong(ChatMessage::timestamp));

            int messageHistorySent = Math.min(previousMessages.size(), MAX_MESSAGE_HISTORY_SENT);
            ObjectMapper mapper = new ObjectMapper();
            messages = mapper.writeValueAsString(previousMessages.subList(0, messageHistorySent));
        } catch(JsonProcessingException e) {
            throw new RuntimeException("LLM Prompt Error - Couldn't deserialize chat history");
        }

        String example = "{ \"message\": \"<reply>\", \"translation\": \"<english-translation>\", \"hints\": [\"this will help\"]}";
        String template = "Aim: [%s], message history: [%s], lesson guide: [%s], example Reply: %s.";
        String systemPrompt = String.format(template, GENERAL_SYSTEM_PROMPT, messages, lessonPrompt, example);

        return new PromptSpec(systemPrompt, userPrompt);
    }
}
