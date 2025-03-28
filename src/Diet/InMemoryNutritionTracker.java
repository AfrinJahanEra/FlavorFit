package src.Diet;

import java.util.*;

public class InMemoryNutritionTracker implements MealLogger, NutritionReporter {
    private final List<Meal> meals = new ArrayList<>();
    private int totalCalories = 0;
    private int totalProtein = 0;
    private int totalCarbs = 0;
    private int totalFat = 0;

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
        NutritionalInfo info = meal.getNutritionalInfo();
        totalCalories += info.getCalories();
        totalProtein += info.getProtein();
        totalCarbs += info.getCarbs();
        totalFat += info.getFat();
    }

    @Override
    public NutritionSummary getNutritionSummary() {
        return new NutritionSummary(totalCalories, totalProtein, totalCarbs, totalFat);
    }

    @Override
    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }
}