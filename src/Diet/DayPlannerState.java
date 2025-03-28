package src.Diet;

import java.util.List;

public class DayPlannerState {
    private final DailyTargets targets;
    private final List<Meal> meals;
    private final List<Exercise> exercises;

    public DayPlannerState(DailyTargets targets, List<Meal> meals, List<Exercise> exercises) {
        this.targets = targets;
        this.meals = meals;
        this.exercises = exercises;
    }

    // Getters
    public DailyTargets getTargets() { return targets; }
    public List<Meal> getMeals() { return meals; }
    public List<Exercise> getExercises() { return exercises; }
}