package com.d_d.aifoodideageneratord_d.services;

import com.d_d.aifoodideageneratord_d.model.Recipe;
import com.d_d.aifoodideageneratord_d.util.DialogsHelper;
import com.d_d.aifoodideageneratord_d.util.RecipeUtils;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class FirestoreService {
    private final Firestore db;

    public FirestoreService() {
        this.db = FirestoreClient.getFirestore();
    }

    public void saveRecipe(String recipeName) {
        Map<String, Object> data = new HashMap<>();
        data.put("content", recipeName);
        String title = RecipeUtils.extractTitleFromContent(recipeName);
        data.put("title", title);

        String documentId = db.collection("recipe").document().getId();

        ApiFuture<WriteResult> future = db.collection("recipe").document(documentId).set(data);

        future.addListener(() -> {
            try {
                WriteResult result = future.get();
                Platform.runLater(() -> DialogsHelper.showSuccessAlert("The recipe was saved with ID: " + documentId));
            } catch (Exception e) {
                Platform.runLater(() -> DialogsHelper.showErrorAlert("Recipe could not be saved : " + e.getMessage()));
            }
        }, Executors.newSingleThreadExecutor());
    }

    public Task<ObservableList<Recipe>> getSavedRecipes() {
        return new Task<>() {
            @Override
            protected ObservableList<Recipe> call() throws Exception {
                ObservableList<Recipe> recipes = FXCollections.observableArrayList();
                ApiFuture<QuerySnapshot> future = db.collection("recipe").get();
                QuerySnapshot querySnapshot = future.get();

                if (querySnapshot != null) {
                    querySnapshot.getDocuments().forEach(documentSnapshot -> {
                        recipes.add(documentSnapshot.toObject(Recipe.class));
                    });
                }

                return recipes;
            }
        };
    }
}
