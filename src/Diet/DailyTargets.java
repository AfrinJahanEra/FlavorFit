package src.Diet;

import java.time.Duration;

public class DailyTargets {
    private final int calorieTarget;
    private final NutritionalInfo macroTargets;
    private final Duration exerciseTarget;

    public DailyTargets(int calorieTarget, NutritionalInfo macroTargets, Duration exerciseTarget) {
        this.calorieTarget = calorieTarget;
        this.macroTargets = macroTargets;
        this.exerciseTarget = exerciseTarget;
    }

    public int getCalorieTarget() { return calorieTarget; }
    public NutritionalInfo getMacroTargets() { return macroTargets; }
    public Duration getExerciseTarget() { return exerciseTarget; }
}