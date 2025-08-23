package com.cakapbot.api.model.chat;

public record UserTurn(
        String message,
        long timestamp
) {}
