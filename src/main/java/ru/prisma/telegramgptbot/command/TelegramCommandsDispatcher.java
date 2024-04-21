package ru.prisma.telegramgptbot.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@AllArgsConstructor
public class TelegramCommandsDispatcher {
    private final List<TelegramCommandsHandler> telegramCommandsHandlers;

    public BotApiMethod<?> processCommand(Update update){

        if(!isCommand(update)){
            throw new IllegalArgumentException("Not a command passed");
        }

        var text = update.getMessage().getText();
        var suitedHandler = telegramCommandsHandlers.stream()
                .filter(it -> it.getSupportedCommand().getCommandValue().equals(text))
                .findAny();

        if(suitedHandler.isEmpty()){
            return SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Not supported command: " + text)
                    .build();
        }
        return suitedHandler.orElseThrow().processCommand(update);

    }

    public boolean isCommand(Update update){
        var text = update.getMessage().getText();
        if(!text.startsWith("/")){
            return false;
        }
        return true;
    }
}
