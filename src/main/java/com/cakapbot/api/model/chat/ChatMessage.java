package com.cakapbot.api.model.chat;

import com.cakapbot.api.enums.ChatRole;

/**
 * Used to represent chat messages stored in memory.
 */
public record ChatMessage(
        ChatRole role,
        String message,
        long timestamp
) {}
