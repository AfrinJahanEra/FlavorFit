package src.Diet;

import java.util.List;

public interface NutritionReporter {
    NutritionSummary getNutritionSummary();
    List<Meal> getMeals();
}
