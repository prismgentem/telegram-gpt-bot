package ru.prisma.telegramgptbot.openai.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
public record Message(
        @JsonProperty("role") String role,
        @JsonProperty("content") String content
        ) {
}
