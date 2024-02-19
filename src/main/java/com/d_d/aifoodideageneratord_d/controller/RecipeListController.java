package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.model.Recipe;
import com.d_d.aifoodideageneratord_d.services.FirestoreService;
import com.d_d.aifoodideageneratord_d.util.DialogsHelper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RecipeListController {

    @FXML
    private ListView<Recipe> recipesListView;

    private final FirestoreService firestoreService = new FirestoreService();

    @FXML
    public void initialize() {
        loadRecipes();
    }

    @FXML
    public void loadRecipes() {
        Task<ObservableList<Recipe>> getRecipesTask = firestoreService.getSavedRecipes();

        getRecipesTask.setOnSucceeded(e -> {
            System.out.println("Recipes loaded: " + getRecipesTask.getValue().size());
            recipesListView.setItems(getRecipesTask.getValue());
        });
        getRecipesTask.setOnFailed(e -> {
            DialogsHelper.showErrorAlert("Could not load recipes.");
            e.getSource().getException().printStackTrace();
        });

        new Thread(getRecipesTask).start();
    }

}

