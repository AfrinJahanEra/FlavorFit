package src.VirtualNutritionist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealService {
    private List<Meal> meals;

    public MealService(String mealFilePath) throws IOException {
        this.meals = loadMealsFromFile(mealFilePath);
    }
    private List<Meal> loadMealsFromFile(String path) throws IOException {
        List<String> lines = FileLoader.loadFile(path);
        List<Meal> meals = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            meals.add(new Meal(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
        }
        return meals;
    }
    public List<Meal> getFilteredMeals(String mealType, List<String> medicalConditions, List<String> allergies) {
        return meals.stream()
                .filter(meal -> meal.getType().equalsIgnoreCase(mealType))
                .filter(meal -> medicalConditions.contains(meal.getMedicalCondition()) || meal.getMedicalCondition().equalsIgnoreCase("all"))
                .filter(meal -> allergies.isEmpty() || meal.isAllergenFree())
                .collect(Collectors.toList());
    }
}
