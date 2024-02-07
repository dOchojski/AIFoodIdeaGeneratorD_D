package com.d_d.aifoodideageneratord_d.services;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;

public class AiRecommendationService {

    private final OpenAiService openAiService;

    public AiRecommendationService() {
        String apiToken = System.getenv("OPENAI_API_KEY");
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalArgumentException("Please set the OPENAI_API_KEY in environment variable.");
        }
        this.openAiService = new OpenAiService(apiToken, Duration.ofSeconds(60));
    }

    public String getRecommendation(List<String> products, String choice) {
        String question;
        if ("sweet".equals(choice)) {
            question = createQuestionSweetRecipe(products);
        } else if ("savoury".equals(choice)) {
            question = createQuestionSavouryRecipe(products);
        } else {
            return "Bad choice";
        }

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

    private String createQuestionSweetRecipe(List<String> products) {
        return "Mam w lodówce następujące produkty : " + String.join(", ", products) + ". Proszę, stwórz przepis kulinarny na słodko używając tylko produktów z tej listy. Możesz nie wykorzystać wszystkich produktów, ale upewnij się, że w przepisie nie ma produktów spoza listy. Wynik proszę przedstawić w czytelnej, uporządkowanej formie, zawierając nazwę przepisu, listę wykorzystanych składników oraz krok po kroku instrukcje przygotowania jeśli z tych produktów nie da sie nic zrobić poinformuj mnie o tym";
    }

    private String createQuestionSavouryRecipe(List<String> products) {
        return "Mam w lodówce następujące produkty : " + String.join(", ", products) + ". Proszę, stwórz przepis kulinarny na słono używając tylko produktów z tej listy. Możesz nie wykorzystać wszystkich produktów, ale upewnij się, że w przepisie nie ma produktów spoza listy. Wynik proszę przedstawić w czytelnej, uporządkowanej formie, zawierając nazwę przepisu, listę wykorzystanych składników oraz krok po kroku instrukcje przygotowania jeśli z tych produktów nie da sie nic zrobić poinformuj mnie o tym";
    }
}
