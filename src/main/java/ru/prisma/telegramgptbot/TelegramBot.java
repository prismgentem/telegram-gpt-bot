package ru.prisma.telegramgptbot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prisma.telegramgptbot.command.TelegramCommandsDispatcher;
import ru.prisma.telegramgptbot.openai.ChatGptService;

import java.util.List;
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final ChatGptService gptService;
    private final TelegramCommandsDispatcher telegramCommandsDispatcher;
    public TelegramBot(
            @Value("${bot.token}") String botToken,
            ChatGptService gptService,
            TelegramCommandsDispatcher telegramCommandsDispatcher
    ) {
        super(new DefaultBotOptions(), botToken);
        this.gptService = gptService;
        this.telegramCommandsDispatcher = telegramCommandsDispatcher;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        try {
            var methods = processUpdate(update);
            methods.forEach(it ->{
                try{
                    sendApiMethod(it);
                }catch (TelegramApiException e){
                    throw new RuntimeException(e);
                }
            });
        }catch (Exception e){
            log.error("Error while processing update" , e);
            sendUserErrorMessage(update.getMessage().getChatId());
        }
    }

    private List<BotApiMethod<?>> processUpdate(Update update){
        if(telegramCommandsDispatcher.isCommand(update)){
            return List.of(telegramCommandsDispatcher.processCommand(update));
        }
        if(update.hasMessage() && update.getMessage().hasText()){
            var text = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();

            String gptGeneratedText = gptService.getResponseChatForUser(chatId, text);
            SendMessage sendMessage = new SendMessage(chatId.toString(), gptGeneratedText);

            return List.of(sendMessage);
        }
        return List.of();
    }

    @SneakyThrows
    private void sendUserErrorMessage(Long userId) {
        sendApiMethod(SendMessage.builder()
                .chatId(userId)
                .text("Произошла ошибка, попробуйте позже")
                .build()
        );
    }

    @Override
    public String getBotUsername(){
        return "My test bot";
    }
}
