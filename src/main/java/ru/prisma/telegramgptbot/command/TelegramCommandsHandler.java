package ru.prisma.telegramgptbot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCommandsHandler {
    BotApiMethod<?> processCommand(Update update);

    TelegramCommands getSupportedCommand();
}
