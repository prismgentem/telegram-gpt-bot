package ru.prisma.telegramgptbot.openai;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prisma.telegramgptbot.openai.api.ChatCompletionRequest;
import ru.prisma.telegramgptbot.openai.api.Message;
import ru.prisma.telegramgptbot.openai.api.OpenAIClient;

@Service
@AllArgsConstructor
public class ChatGptService {
    private final OpenAIClient openAIClient;
    private final ChatGptHistoryService chatGptHistoryService;
    @Nonnull
    public String getResponseChatForUser(
            Long userId,
            String inputText
    ){
        chatGptHistoryService.createHistoryIfNotExist(userId);
        var history = chatGptHistoryService.addMessageToHistory(
                userId,
                Message.builder()
                        .content(inputText)
                        .role("user")
                        .build()
        );


        var request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(history.chatMessages())
                .build();

        var response = openAIClient.createChatCompletion(request);

        var messageFromGpt = response.choices().get(0).message();

        chatGptHistoryService.addMessageToHistory(userId, messageFromGpt);

        return response.choices().get(0).message().content();
    }

}
