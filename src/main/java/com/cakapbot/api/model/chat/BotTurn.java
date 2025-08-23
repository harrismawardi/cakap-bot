package com.cakapbot.api.model.chat;

import java.util.List;

public record BotTurn (
        String message,
        String translation,
        List<String> hints,
        long timestamp
){}
