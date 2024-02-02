package com.d_d.aifoodideageneratord_d.services;

import com.d_d.aifoodideageneratord_d.config.ConfigLoader;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class AiRecommendationService {

    private final OpenAiService openAiService;

    public AiRecommendationService() {
        Properties properties = ConfigLoader.loadProperties("src/main/resources/application.properties");
        this.openAiService = new OpenAiService(properties.getProperty("token"), Duration.ofSeconds(60));
    }

    public String getRecommendation(List<String> products) {
        String question = createQuestion(products);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model("gpt-4-turbo-preview")
                .build();

        ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(request);
        return chatCompletionResult.getChoices().stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .findFirst()
                .orElse("Nie udało się uzyskać odpowiedzi");
    }

    private String createQuestion(List<String> products) {
        return "Mam w lodówce następujące produkty : " + String.join(", ", products) + ". Proszę, stwórz przepis kulinarny używając tylko produktów z tej listy. Możesz nie wykorzystać wszystkich produktów, ale upewnij się, że w przepisie nie ma produktów spoza listy. Wynik proszę przedstawić w czytelnej, uporządkowanej formie, zawierając nazwę przepisu, listę wykorzystanych składników oraz krok po kroku instrukcje przygotowania";
    }
}
