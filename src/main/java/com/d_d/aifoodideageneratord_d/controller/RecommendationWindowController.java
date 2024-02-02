package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.services.AiRecommendationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class RecommendationWindowController {

    @FXML
    private Label recommendationLabel;

    private final AiRecommendationService recommendationService = new AiRecommendationService();

    public void recommend(List<String> products) {
        String recommendation = recommendationService.getRecommendation(products);
        recommendationLabel.setText(recommendation);
    }
}
