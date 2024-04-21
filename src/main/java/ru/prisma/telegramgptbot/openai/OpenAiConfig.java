package ru.prisma.telegramgptbot.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.prisma.telegramgptbot.openai.api.OpenAIClient;

@Configuration
public class OpenAiConfig {
    @Bean
    public OpenAIClient openAIClient(
            @Value("${openai.token}") String botToken,
            RestTemplateBuilder restTemplateBuilder
    ){
        return new OpenAIClient(botToken, restTemplateBuilder.build());
    }
}
