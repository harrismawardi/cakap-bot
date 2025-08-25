package com.cakapbot.api.application;

import com.cakapbot.api.ports.LlmClient;
import com.cakapbot.api.domain.ai.PromptSpec;
import com.cakapbot.api.domain.chat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AiReplyService {
    private final LlmClient llmClient;
    private final PromptSpecComposer promptSpecComposer;

    public AiReplyService(@Autowired @Qualifier("openAiClient") LlmClient llmClient, PromptSpecComposer composer) {
        this.llmClient = llmClient;
        this.promptSpecComposer = composer;
    }

    public BotTurn generateReply(UserTurn userTurn, ChatHistory chatHistory) {
        PromptSpec spec = promptSpecComposer.compose(userTurn.message(), chatHistory.getMessages(), chatHistory.getSystemPrompt());
        return llmClient.complete(spec);
    }
}
