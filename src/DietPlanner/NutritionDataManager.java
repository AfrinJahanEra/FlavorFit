package src.DietPlanner;

import java.util.Map;
import java.io.*;
import java.util.*;


class NutritionDataManager implements FoodRepository, TargetStorage {
    private static final String NUTRITION_FILE = "nutrition.txt";
    private static final String TARGETS_FILE = "targets.txt";
    private final Map<String, Food> foodDatabase = new HashMap<>();

    @Override
    public void loadFoodDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NUTRITION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    Food food = new Food(parts[0], Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]));
                    foodDatabase.put(parts[0].toLowerCase(), food);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading food database: " + e.getMessage());
        }
    }

    @Override
    public Optional<Food> findFood(String foodName) {
        return Optional.ofNullable(foodDatabase.get(foodName.toLowerCase()));
    }

    @Override
    public UserTarget loadTargets() {
        UserTarget target = new UserTarget();
        try (BufferedReader reader = new BufferedReader(new FileReader(TARGETS_FILE))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    target.setCalorieTarget(Integer.parseInt(parts[0]));
                    target.setProteinTarget(Double.parseDouble(parts[1]));
                    target.setCarbsTarget(Double.parseDouble(parts[2]));
                    target.setFatTarget(Double.parseDouble(parts[3]));
                    target.setExerciseTarget(Integer.parseInt(parts[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing targets found. Using default values.");
        }
        return target;
    }

    @Override
    public void saveTargets(UserTarget userTarget) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TARGETS_FILE))) {
            writer.printf("%d,%.1f,%.1f,%.1f,%d",
                    userTarget.getCalorieTarget(),
                    userTarget.getProteinTarget(),
                    userTarget.getCarbsTarget(),
                    userTarget.getFatTarget(),
                    userTarget.getExerciseTarget());
        } catch (IOException e) {
            System.out.println("Error saving targets: " + e.getMessage());
        }
    }
}