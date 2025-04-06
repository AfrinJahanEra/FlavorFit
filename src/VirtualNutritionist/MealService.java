package src.VirtualNutritionist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import src.FileManager.FileLoader;
import src.FileManager.SimpleFileLoader;

public class MealService {
    private final List<Meal> meals;

    public MealService(String mealFilePath) throws IOException {
        this.meals = loadMealsFromFile(mealFilePath);
    }

    private List<Meal> loadMealsFromFile(String path) throws IOException {
        FileLoader<String> fileLoader = new SimpleFileLoader();
        List<String> lines = fileLoader.loadFromFile(path);
        List<Meal> meals = new ArrayList<>();
        
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length < 4) {
                System.err.println("Invalid meal entry: " + line);
                continue;
            }
            
            try {
                meals.add(new Meal(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
            } catch (Exception e) {
                System.err.println("Error parsing meal: " + line);
                e.printStackTrace();
            }
        }
        return meals;
    }

    public List<Meal> getFilteredMeals(String mealType, List<String> medicalConditions, List<String> allergies) {
        if (mealType == null || medicalConditions == null || allergies == null) {
            return new ArrayList<>();
        }
        
        return meals.stream()
            .filter(meal -> meal != null && meal.getType().equalsIgnoreCase(mealType))
            .filter(meal -> medicalConditions.stream().anyMatch(mc -> 
                mc.equalsIgnoreCase(meal.getMedicalCondition()) || 
                "all".equalsIgnoreCase(meal.getMedicalCondition()))
            )
            .filter(meal -> allergies.isEmpty() || meal.isAllergenFree())
            .collect(Collectors.toList());
    }
}