package ru.prisma.telegramgptbot.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.prisma.telegramgptbot.command.TelegramCommands;
import ru.prisma.telegramgptbot.command.TelegramCommandsHandler;
import ru.prisma.telegramgptbot.openai.ChatGptHistoryService;

@Component
@AllArgsConstructor
public class ChatGptHistoryCommandHandler implements TelegramCommandsHandler {
    private final ChatGptHistoryService chatGptHistoryService;
    private final String CLEAR_MESSAGE = "История диалога отчищена, контекст обнулён.";
    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptHistoryService.clearHistory(update.getMessage().getChatId());
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(CLEAR_MESSAGE.formatted(
                        update.getMessage().getChat().getFirstName()
                ))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CLEAR_COMMAND;
    }
}
