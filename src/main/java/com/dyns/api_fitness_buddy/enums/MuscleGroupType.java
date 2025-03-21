package com.dyns.api_fitness_buddy.enums;

public enum MuscleGroupType {
    TRAPS("Traps"),
    SHOULDERS("Shoulders"),
    CHEST("Chest"),
    BICEPS("Biceps"),
    FOREARMS("Forearms"),
    ABS("Abs"),
    QUADS("Quads"),
    CALVES("Calves"),
    LATS("Lats"),
    TRICEPS("Triceps"),
    GLUTES("Glutes"),
    HAMSTRINGS("Hamstrings");

    public final String label;

    private MuscleGroupType(String label) {
        this.label = label;
    }
}
