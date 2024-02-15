package com.d_d.aifoodideageneratord_d.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitializer {
    private static final String CONFIG_PATH = "src/main/resources/aieatproompt-firebase-adminsdk-itb1t-125108c2e4.json";
    private static final String DATABASE_URL = "https://aieatproompt.firebaseio.com";

    public static void initialize() throws IOException {
            FirebaseOptions options = buildFirebaseOptions();
            initializeFirebase(options);
            Firestore db = FirestoreClient.getFirestore();
            db.collection("recipe").document().collection(DATABASE_URL);
    }

    private static FirebaseOptions buildFirebaseOptions() throws IOException {
        try {
            InputStream serviceAccount = FirebaseInitializer.class.getResourceAsStream(CONFIG_PATH);
            if (serviceAccount != null) {
                return new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
            }
        } catch (IOException e) {
            throw new IOException("Failed to initialize Firebase", e);
        }
        return null;
    }

    private static void initializeFirebase(FirebaseOptions options) {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
