package ru.prisma.telegramgptbot.openai;

import lombok.Builder;
import ru.prisma.telegramgptbot.openai.api.Message;

import java.util.List;
@Builder
public record ChatHistory(List<Message> chatMessages) {
}
