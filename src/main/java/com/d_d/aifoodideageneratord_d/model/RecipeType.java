package com.d_d.aifoodideageneratord_d.model;

public enum RecipeType {
    SWEET("słodko"),
    SAVOURY("słono");

    private final String description;

    RecipeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static RecipeType fromChoice(String choice) {
        for (RecipeType value : values()) {
            if (value.name().equalsIgnoreCase(choice)) {
                return value;
            }
        }
        return null;
    }
}
