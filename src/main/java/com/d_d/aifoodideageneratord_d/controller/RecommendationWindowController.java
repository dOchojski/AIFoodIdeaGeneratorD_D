package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.services.AiRecommendationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class RecommendationWindowController {

    @FXML
    private Label recommendationLabel;

    private final AiRecommendationService recommendationService = new AiRecommendationService();

    public void recommend(List<String> products, String choice) {
        String recommendation = "";

        if ("sweet".equals(choice)) {
            recommendation = recommendationService.getRecommendation(products, "sweet");
        } else if ("savoury".equals(choice)) {
            recommendation = recommendationService.getRecommendation(products, "savoury");
        }
        recommendationLabel.setText(recommendation);
    }
}
