package com.cakapbot.api.client;

import com.cakapbot.api.interfaces.LlmClient;
import com.cakapbot.api.model.ai.PromptSpec;
import com.cakapbot.api.model.chat.BotTurn;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service("googleGeminiClient")
public class GoogleGeminiClient implements LlmClient {

    public BotTurn complete(PromptSpec spec) {
        return new BotTurn("sup", "sup", List.of(), Instant.now().getEpochSecond());
    }

}
