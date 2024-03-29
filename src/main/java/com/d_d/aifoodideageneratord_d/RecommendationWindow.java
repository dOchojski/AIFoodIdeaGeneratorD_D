package com.d_d.aifoodideageneratord_d;

import com.d_d.aifoodideageneratord_d.controller.RecommendationWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecommendationWindow {

    private List<String> products;
    private String choice;

    public RecommendationWindow(List<String> products, String choice) {
        this.products = products;
        this.choice = choice;
    }

    public void open() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecommendationWindow.class.getResource("recommendation-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Co mogę zjeść");
        stage.setScene(scene);
        stage.show();

        RecommendationWindowController controller = fxmlLoader.getController();
        controller.recommend(products, choice);
    }
}
