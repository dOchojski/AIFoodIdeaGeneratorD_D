package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.RecommendationWindow;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProductListController {

    @FXML
    private TextField product;

    @FXML
    private ListView<String> productList;

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