package ru.prisma.telegramgptbot.openai.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.prisma.telegramgptbot.openai.api.ChatCompletionRequest;
import ru.prisma.telegramgptbot.openai.api.ChatCompletionResponse;

@AllArgsConstructor
public class OpenAIClient {
    private final String token;
    private final RestTemplate restTemplate;

    public ChatCompletionResponse createChatCompletion(ChatCompletionRequest request){
        String url = "https://api.proxyapi.ru/openai/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "Bearer " + token);

        //можно накинуть отлов ошибок
        HttpEntity<ChatCompletionRequest> httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<ChatCompletionResponse> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, httpEntity, ChatCompletionResponse.class
        );

        return responseEntity.getBody();
    }

}
