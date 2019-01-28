package com.example.myProject.enums;

public enum MuscleGroups {
    LEGS("LEGS"),
    BACK("BACK"),
    CHEST("CHEST"),
    BICEPS("BICEPS"),
    TRICEPS("TRICEPS"),
    ABS("ABS");

    private String displayName;

    MuscleGroups(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}