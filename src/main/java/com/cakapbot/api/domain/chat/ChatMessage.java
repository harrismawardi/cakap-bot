package com.cakapbot.api.domain.chat;

/**
 * Used to represent chat messages stored in memory.
 */
public record ChatMessage(
        ChatRole role,
        String message,
        long timestamp
) {}
