package com.cakapbot.api.application;

import com.cakapbot.api.domain.ai.PromptSpec;
import com.cakapbot.api.domain.chat.ChatMessage;
import com.cakapbot.api.domain.lesson.Lesson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PromptSpecComposer {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final int MAX_MESSAGE_HISTORY_SENT = 10;
    private static final String GENERAL_SYSTEM_PROMPT =
        "STYLE: Simple phrases. 2 short sentences max. Ignore small typos. " +
        "CONTEXT USE: Read the chat history and keep continuity, but don’t restate concluded topics unless asked. " +
        "ERROR HANDLING: If unclear, ask a short clarifying question. If the user says they don’t understand, rephrase more simply. " +
        "DO: " +
          "- Ask one follow-up question at a time." +
          "- Provide the english translation of response." +
          "- Give hint on how to reply to your message in english." +
        "DON’T:" +
        "- Don’t switch topics away from scope" +
        "- Don’t output meta-comments about rules or JSON." +
        "- Don't use religious phrases" +
        "EXAMPLE RESPONSE: { \"message\": \"<reply>\", \"translation\": \"<english-translation>\", \"hints\": [\"<how to respond to this>\"]}";


    private static final String PERSONA_PROMPT = "You are friendly and eager to learn more about the people you talk to. keep tone warm, curious.";
    private static final String LANGUAGE_PROMPT_TEMPLATE = "Use the target locale {locale}. Default to that language. If the user writes in another language, briefly answer once and ask them to continue in {locale}.";

    /**
     * Generates the base system prompt outlining LLM behaviour and objectives during lesson chat. Includes persona the LLM will adopt,
     * lesson specific guidelines, and high-level global guidelines for the LLM to adhere to.
     * @return System prompt to populate in {@link PromptSpec}
     */
    public String composeSystemPrompt(Lesson lesson) {
        String languagePrompt = LANGUAGE_PROMPT_TEMPLATE.replace("{locale}", lesson.languageCode());

        return  "ROLE: " + PERSONA_PROMPT +
                "LANGUAGE: " + languagePrompt +
                "SCOPE: " + lesson.lessonScope() +
                GENERAL_SYSTEM_PROMPT;
    }

    /**
     * Wrapper for the user and system prompts to query LLM for chat-bot reply.
     *
     * @param userPrompt - last user message in chat
     * @param previousMessages - history of messages in chat
     * @param baseSystemPrompt - base prompt for LLM behaviour and objectives.
     * @return full prompt-spec to provide to LLM.
     */
    public PromptSpec composeFullPromptSpec(String userPrompt, List<ChatMessage> previousMessages, String baseSystemPrompt) {
        Map<String, String> systemPromptMap = new HashMap<>();
        systemPromptMap.put("conversationHistory", getTruncatedMessageHistory(previousMessages));
        systemPromptMap.put("system", baseSystemPrompt);

        return new PromptSpec(mapToString(systemPromptMap), userPrompt);
    }

    private String getTruncatedMessageHistory(List<ChatMessage> previousMessages) {
        previousMessages.sort(Comparator.comparingLong(ChatMessage::timestamp));
        int messageHistorySent = Math.min(previousMessages.size(), MAX_MESSAGE_HISTORY_SENT);
        return mapToString(previousMessages.subList(0, messageHistorySent));
    }

    private String mapToString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch(JsonProcessingException e) {
            throw new RuntimeException("LLM Prompt Error - Couldn't deserialize prompt object");
        }
    }
}
