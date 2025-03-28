package src.Diet;

import java.time.Duration;

public class DayPlannerService {
    private final MealLogger mealLogger;
    private final NutritionReporter nutritionReporter;
    private final ExerciseLogger exerciseLogger;
    private final FitnessReporter fitnessReporter;
    private final AlarmScheduler alarmScheduler;
    private final DailyTargets targets;

    public DayPlannerService(DailyTargets targets,
                           MealLogger mealLogger,
                           NutritionReporter nutritionReporter,
                           ExerciseLogger exerciseLogger,
                           FitnessReporter fitnessReporter,
                           AlarmScheduler alarmScheduler) {
        this.targets = targets;
        this.mealLogger = mealLogger;
        this.nutritionReporter = nutritionReporter;
        this.exerciseLogger = exerciseLogger;
        this.fitnessReporter = fitnessReporter;
        this.alarmScheduler = alarmScheduler;
    }

    public void addMeal(Meal meal) {
        mealLogger.addMeal(meal);
        checkNutritionAlerts();
    }

    public void addExercise(Exercise exercise) {
        exerciseLogger.addExercise(exercise);
        checkExerciseAlerts();
    }

    private void checkNutritionAlerts() {
        NutritionSummary summary = nutritionReporter.getNutritionSummary();
        if (summary.getTotalCalories() > targets.getCalorieTarget()) {
            alarmScheduler.scheduleRelativeAlarm(0, "Calorie target exceeded!");
        }
    }

    private void checkExerciseAlerts() {
        FitnessSummary summary = fitnessReporter.getFitnessSummary();
        if (summary.getTotalExerciseTime().compareTo(targets.getExerciseTarget()) > 0) {
            alarmScheduler.scheduleRelativeAlarm(0, "Exercise target achieved!");
        }
    }

    public void startExerciseTimer(Duration duration) {
        alarmScheduler.scheduleRelativeAlarm(duration.toMinutes(), "Exercise timer completed!");
    }

    public DailySummary generateDailySummary(AlarmMonitor alarmMonitor) {
        return new DailySummary(
            nutritionReporter.getNutritionSummary(),
            fitnessReporter.getFitnessSummary(),
            alarmMonitor.getTriggeredAlarms(),
            targets
        );
    }
}