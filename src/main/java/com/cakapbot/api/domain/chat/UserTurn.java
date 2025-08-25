package com.cakapbot.api.domain.chat;

public record UserTurn(
        String message,
        long timestamp
) {}
