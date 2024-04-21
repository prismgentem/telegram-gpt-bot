package ru.prisma.telegramgptbot.command.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.prisma.telegramgptbot.command.TelegramCommands;
import ru.prisma.telegramgptbot.command.TelegramCommandsHandler;

@Component
public class StartCommandHandler implements TelegramCommandsHandler {
    private final String HELLO_MESSAGE = """
            Привет %s!
            Этот бот реализует общение с Chat-GPT через Telegram!
            """;
    @Override
    public BotApiMethod<?> processCommand(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(HELLO_MESSAGE.formatted(
                        update.getMessage().getChat().getFirstName()
                ))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START_COMMAND;
    }
}
