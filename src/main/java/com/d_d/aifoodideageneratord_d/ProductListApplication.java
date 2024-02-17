package com.d_d.aifoodideageneratord_d;

import com.d_d.aifoodideageneratord_d.controller.FirebaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ProductListApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FirebaseInitializer.initialize();

        } catch (IOException e) {
            System.out.println("Failed to initialize Firebase");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ProductListApplication.class.getResource("product-list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Products List");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}