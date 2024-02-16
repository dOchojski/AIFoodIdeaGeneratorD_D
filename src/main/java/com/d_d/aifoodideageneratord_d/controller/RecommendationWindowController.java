package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.services.AiRecommendationService;
import com.d_d.aifoodideageneratord_d.services.FirestoreService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

public class RecommendationWindowController {

    @FXML
    private Label recommendationLabel;

    @FXML
    private Button saveReceiptButton;

    private AiRecommendationService recommendationService;

    private FirestoreService firestoreService;

    public void initialize() {
        this.firestoreService = new FirestoreService();
        this.recommendationService = new AiRecommendationService();
    }

    public void recommend(List<String> products, String choice) {
        try {
            String recommendation = "";

            if ("sweet".equals(choice)) {
                recommendation = recommendationService.getRecommendation(products, "sweet");
            } else if ("savoury".equals(choice)) {
                recommendation = recommendationService.getRecommendation(products, "savoury");
            }
            recommendationLabel.setText(recommendation);
        } catch (Exception exception) {
            recommendationLabel.setText("Nie udało się znaleźć przepisu");
            saveReceiptButton.setVisible(false);
        }
    }

    @FXML
    private void handleSaveRecipe() {
        String recipeContent = recommendationLabel.getText();
        firestoreService.saveRecipe(recipeContent);
    }
}
