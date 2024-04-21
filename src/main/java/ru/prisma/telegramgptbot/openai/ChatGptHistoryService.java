package ru.prisma.telegramgptbot.openai;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prisma.telegramgptbot.openai.api.Message;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChatGptHistoryService {
    private final Map<Long, ChatHistory> chatHistoryMap = new ConcurrentHashMap<>();

    public Optional<ChatHistory> getUserHistory(Long userId){
        return Optional.ofNullable(chatHistoryMap.get(userId));
    }

    public void createHistory(Long userId){
        chatHistoryMap.put(userId, new ChatHistory(new ArrayList<>()));
    }

    public void clearHistory(Long userId){
        chatHistoryMap.remove(userId);
    }

    public ChatHistory addMessageToHistory(Long userId, Message message){
        var chatHistory = chatHistoryMap.get(userId);

        if(chatHistory == null){
            throw new IllegalStateException("History not exist for user = " + userId);
        }

        chatHistory.chatMessages().add(message);
        return chatHistory;
    }

    public void createHistoryIfNotExist(Long userId) {
        if(!chatHistoryMap.containsKey(userId)){
            createHistory(userId);
        }
    }
}
