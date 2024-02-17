package com.d_d.aifoodideageneratord_d.services;

import com.d_d.aifoodideageneratord_d.util.DialogsHelper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.scene.control.Alert;

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
}
