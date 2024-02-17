package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.services.AiRecommendationService;
import com.d_d.aifoodideageneratord_d.services.FirestoreService;
import com.d_d.aifoodideageneratord_d.services.PdfExportService;
import com.d_d.aifoodideageneratord_d.util.DialogsHelper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class RecommendationWindowController {

    @FXML
    private Label recommendationLabel;

    @FXML
    private Button saveReceiptButton;

    @FXML
    private Button exportToPdfButton;

    private AiRecommendationService recommendationService;
    private PdfExportService pdfExportService;

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
            recommendationLabel.setText("Couldn't find recipe");
            saveReceiptButton.setVisible(false);
        }
    }

    @FXML
    private void handleSaveRecipe() {
        String recipeContent = recommendationLabel.getText();
        Task<Void> saveTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                firestoreService.saveRecipe(recipeContent);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };
        new Thread(saveTask).start();
    }

    @FXML
    private void handleExportToPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save recipe to PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dokument PDF", "*.pdf"));
        Stage stage = (Stage) recommendationLabel.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                pdfExportService.exportRecipeToPdf(recommendationLabel.getText(), file);
                DialogsHelper.showSuccessAlert("The recipe has been successfully saved to a PDF file");
            } catch (Exception e) {
                DialogsHelper.showErrorAlert("Failed to export recipe to PDF: " + e.getMessage());
                exportToPdfButton.setVisible(false);
            }

        }
    }
}


