package com.example.myProject.enums;

public enum MuscleGroups {
    LEGS("legs"),
    BACK("back"),
    CHEST("chest"),
    BICEPS("biceps"),
    TRICEPS("triceps"),
    ABS("abs");

    private String displayName;

    MuscleGroups(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
