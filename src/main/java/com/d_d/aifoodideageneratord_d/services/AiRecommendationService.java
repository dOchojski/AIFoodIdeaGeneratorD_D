package com.d_d.aifoodideageneratord_d.services;

import com.d_d.aifoodideageneratord_d.model.RecipeType;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;

public class AiRecommendationService {

    private static final String MODEL = "gpt-4-turbo-preview";
    private static final String OPENAI_API_KEY_ENV = "OPENAI_API_KEY";
    private final OpenAiService openAiService;

    public AiRecommendationService() {
        String apiToken = System.getenv(OPENAI_API_KEY_ENV);
        validApiToken(apiToken);
        this.openAiService = new OpenAiService(apiToken, Duration.ofSeconds(60));
    }

    public String getRecommendation(List<String> products, String choice) {
        RecipeType recipeType = RecipeType.fromChoice(choice);
        if (recipeType == null) {
            throw new IllegalArgumentException("Invalid choice");
        }

        String question = createQuestion(products, recipeType);
        ChatCompletionRequest request = buildChatCompletionRequest(question);
        return getChatCompletionResult(request);
    }

    private String createQuestion(List<String> products, RecipeType recipeType) {
        String productList = String.join(", ", products);
        return String.format("I have the following products in my fridge: %s. " +
                "Create a recipe using only the items in this list. " +
                "You do not have to use all the products, but make sure that the recipe does not contain anything that is not on the list. " +
                "Present the result in a clear, organised way, always starting with 'Recipe Title:', including the name of the recipe, a list of ingredients used including grams, preparation time, calories, step-by-step instructions. " +
                "If it is not possible to make something with these products, please let me know and let the answer be in Polish..", productList, recipeType.getDescription());
    }

    private ChatCompletionRequest buildChatCompletionRequest(String question) {
        return ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model(MODEL)
                .build();
    }

    private String getChatCompletionResult(ChatCompletionRequest request) {
        ChatCompletionResult result = openAiService.createChatCompletion(request);
        return result.getChoices().stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .findFirst()
                .orElse("Failed to get a response");
    }

    private void validApiToken(String apiToken) {
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalArgumentException("Please set the OPENAI_API_KEY in environment variable");
        }
    }
}
