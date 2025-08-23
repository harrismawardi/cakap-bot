package com.cakapbot.api.interfaces;

import com.cakapbot.api.model.ai.PromptSpec;
import com.cakapbot.api.model.chat.BotTurn;

public interface LlmClient {
    BotTurn complete(PromptSpec spec);
}
