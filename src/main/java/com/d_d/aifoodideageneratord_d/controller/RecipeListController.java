package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.model.Recipe;
import com.d_d.aifoodideageneratord_d.services.FirestoreService;
import com.d_d.aifoodideageneratord_d.util.DialogsHelper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class RecipeListController {

    @FXML
    private ListView<Recipe> recipesListView;

    private final FirestoreService firestoreService = new FirestoreService();

    @FXML
    public void initialize() {
        loadRecipes();
        recipesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showRecipeDetails(newSelection);
            }
        });
    }


    @FXML
    public void loadRecipes() {
        Task<ObservableList<Recipe>> getRecipesTask = firestoreService.getSavedRecipes();

        getRecipesTask.setOnSucceeded(e -> {
            recipesListView.setItems(getRecipesTask.getValue());
            recipesListView.setCellFactory(param -> new ListCell<Recipe>() {
                @Override
                protected void updateItem(Recipe recipe, boolean empty) {
                    super.updateItem(recipe, empty);
                    setText(empty || recipe == null ? null : recipe.getTitle());
                }
            });
        });
        getRecipesTask.setOnFailed(e -> {
            DialogsHelper.showErrorAlert("Could not load recipes.");
            e.getSource().getException().printStackTrace();
        });

        new Thread(getRecipesTask).start();
    }

    private void showRecipeDetails(Recipe recipe) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Szczegóły przepisu");
        alert.setHeaderText(recipe.getTitle());
        alert.setContentText(recipe.getContent());
        alert.showAndWait();
    }
}

