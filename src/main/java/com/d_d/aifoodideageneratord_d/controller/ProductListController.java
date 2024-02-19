package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.RecommendationWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProductListController {

    @FXML
    private TabPane mainTabPane;

    @FXML
    private TextField product;

    @FXML
    private ListView<String> productList;

    @FXML
    public void initialize() {
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Przepisy".equals(newValue.getText()) && newValue.getContent() == null) {
                try {
                    loadRecipesTabContent(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadRecipesTabContent(Tab recipesTab) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/d_d/aifoodideageneratord_d/recipe-list-view.fxml"));
        Node recipesView = loader.load();
        recipesTab.setContent(recipesView);
    }

    @FXML
    protected void onAddProduct() {
        String text = product.getText();
        productList.getItems().add(text);
        product.clear();
    }

    @FXML
    protected void onDeleteProduct() {
        String selectedItem = productList.getSelectionModel().getSelectedItem();
        productList.getItems().remove(selectedItem);
        product.clear();
    }

    @FXML
    protected void recommendSweetRecipe() throws IOException {
        new RecommendationWindow(productList.getItems(), "sweet").open();
    }

    @FXML
    protected void recommendSavouryRecipe() throws IOException {
        new RecommendationWindow(productList.getItems(), "savoury").open();
    }
}