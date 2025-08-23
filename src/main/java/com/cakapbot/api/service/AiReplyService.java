package com.cakapbot.api.service;

import com.cakapbot.api.interfaces.LlmClient;
import com.cakapbot.api.model.ai.PromptSpec;
import com.cakapbot.api.model.chat.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AiReplyService {

    private static final String GENERAL_SYSTEM_PROMPT = """
            You are an AI powered chatbot for learning.
            Generate a reply to the chat to help the user to learn.
            The reply must match the provided json schema.
            For each reply should be the english translation and hints (in English)
            that can be used to aid the user in responding.
            This chat is for a specific topic. Adhere to the lesson guide when generating the response.
            Keep messages short - like texting.
            """;

    private final LlmClient llmClient;
    private final int MAX_MESSAGE_HISTORY_SENT = 10;

    public AiReplyService(@Autowired @Qualifier("googleGeminiClient") LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    public BotTurn generateReply(UserTurn userTurn, ChatHistory chatHistory) {
        PromptSpec spec = composePromptSpec(userTurn.message(), chatHistory.getMessages(), chatHistory.getSystemPrompt());
        return llmClient.complete(spec);
    }

    private PromptSpec composePromptSpec(String userPrompt, List<ChatMessage> previousMessages, String lessonPrompt) {
        String messages;
        try {
            previousMessages.sort(Comparator.comparingLong(ChatMessage::timestamp));

            int messageHistorySent = Math.min(previousMessages.size(), MAX_MESSAGE_HISTORY_SENT);
            ObjectMapper mapper = new ObjectMapper();
            messages = mapper.writeValueAsString(previousMessages.subList(0, messageHistorySent));
        } catch(JsonProcessingException e) {
            throw new RuntimeException("LLM Prompt Error - Couldn't deserialize chat history");
        }

        String example = "{ \"message\": \"<reply>\", \"translation\": \"{<english-translation>\", \"hints\": [\"this will help\"]}";
        String template = "Aim: [%s], message history: [%s], lesson guide: [%s], example Reply: %s.";
        String systemPrompt = String.format(template, GENERAL_SYSTEM_PROMPT, messages, lessonPrompt, example);

        return new PromptSpec(systemPrompt, userPrompt);
    }
}
