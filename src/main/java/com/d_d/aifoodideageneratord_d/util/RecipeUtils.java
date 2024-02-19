package com.d_d.aifoodideageneratord_d.util;

public class RecipeUtils {
    public static String extractTitleFromContent(String content) {
        final String titleIdentifier = "Recipe Title:";
        int titleIndex = content.indexOf(titleIdentifier);

        if (titleIndex != -1) {
            int titleEndIndex = content.indexOf("\n", titleIndex);
            return content.substring(titleIndex + titleIdentifier.length(), titleEndIndex).trim();
        }

        return "No Title";
    }
}
