package com.cakapbot.api.ports;

import com.cakapbot.api.domain.ai.PromptSpec;
import com.cakapbot.api.domain.chat.BotTurn;

public interface LlmClient {
    BotTurn complete(PromptSpec spec);
}
