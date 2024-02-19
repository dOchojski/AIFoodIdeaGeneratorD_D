package com.d_d.aifoodideageneratord_d.controller;

import com.d_d.aifoodideageneratord_d.model.Recipe;
import javafx.fxml.FXML;

import java.awt.*;

public class RecipeDetailsController {
    @FXML
    private TextArea detailsTextArea;

    public void setRecipe(Recipe recipe) {
        detailsTextArea.setText(recipe.getContent());
    }
}

