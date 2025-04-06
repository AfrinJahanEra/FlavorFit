package src.DietPlanner;

import java.io.*;
import java.util.*;
import src.FileManager.FileLoader;


class NutritionDataManager implements FileLoader<Food>, TargetStorage {
    
    private static final String NUTRITION_FILE = "nutrition.txt";
    private static final String TARGETS_FILE = "targets.txt";
    private final Map<String, Food> foodDatabase = new HashMap<>();

    @Override
    public List<Food> loadFromFile(String filePath) throws IOException {
        List<Food> foods = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    Food food = new Food(
                        parts[0], 
                        Integer.parseInt(parts[1]),
                        Double.parseDouble(parts[2]), 
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4])
                    );
                    foods.add(food);
                    foodDatabase.put(parts[0].toLowerCase(), food);
                }
            }
        }
        return foods;
    }

    @Override
    public void validateFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Invalid file: " + filePath);
        }
    }

    public Optional<Food> findFood(String foodName) {
        return Optional.ofNullable(foodDatabase.get(foodName.toLowerCase()));
    }

    // This can now be replaced with loadFromFile() but kept for backward compatibility
    public void loadFoodDatabase() {
        try {
            loadFromFile(NUTRITION_FILE);
        } catch (IOException e) {
            System.out.println("Error loading food database: " + e.getMessage());
        }
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