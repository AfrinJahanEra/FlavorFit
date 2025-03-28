package src.Diet;

import java.util.List;

public class DailySummary {
    private final NutritionSummary nutrition;
    private final FitnessSummary fitness;
    private final List<String> alarms;
    private final DailyTargets targets;

    public DailySummary(NutritionSummary nutrition, FitnessSummary fitness, 
                      List<String> alarms, DailyTargets targets) {
        this.nutrition = nutrition;
        this.fitness = fitness;
        this.alarms = alarms;
        this.targets = targets;
    }

    // Getters
    public NutritionSummary getNutrition() { return nutrition; }
    public FitnessSummary getFitness() { return fitness; }
    public List<String> getAlarms() { return alarms; }
    public DailyTargets getTargets() { return targets; }
}