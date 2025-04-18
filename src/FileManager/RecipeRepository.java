package src.FileManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import src.FoodRecipes.Recipe;
import src.Interface.FileLoader;

public class RecipeRepository implements FileLoader<Recipe> {
    private final String recipesPath;

    public RecipeRepository(String recipesPath) {
        if (recipesPath == null || recipesPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipes path cannot be null or empty");
        }
        this.recipesPath = recipesPath;
    }

    @Override
    public List<Recipe> loadFromFile(String filePath) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        File folder = new File(filePath);
        validateFile(filePath);

        File[] recipeFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (recipeFiles == null) {
            return recipes;
        }

        for (File file : recipeFiles) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), StandardCharsets.UTF_8))) {
                
                String name = readNonNullLine(br, file, "name");
                String goal = readNonNullLine(br, file, "goal");
                List<String> ingredients = readSection(br, file, "ingredients");
                List<String> steps = readSection(br, file, "steps");

                recipes.add(new Recipe(name.trim(), goal.trim(), ingredients, steps));
            } catch (IOException e) {
                System.err.printf("Error reading file %s: %s%n", file.getName(), e.getMessage());
                throw e;
            }
        }
        return recipes;
    }

    private String readNonNullLine(BufferedReader br, File file, String fieldName) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IOException(String.format(
                "Missing %s in recipe file: %s", fieldName, file.getName()));
        }
        return line;
    }

    private List<String> readSection(BufferedReader br, File file, String sectionName) throws IOException {
        List<String> section = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.equals("---")) {
                break; // Section separator found
            }
            if (!trimmed.isEmpty()) {
                section.add(trimmed);
            }
        }
        if (section.isEmpty()) {
            System.err.printf("Warning: Empty %s section in file: %s%n", sectionName, file.getName());
        }
        return section;
    }

    @Override
    public void validateFile(String filePath) throws IOException {
        File folder = new File(filePath);
        if (!folder.exists()) {
            throw new IOException("Recipes directory does not exist: " + filePath);
        }
        if (!folder.isDirectory()) {
            throw new IOException("Path is not a directory: " + filePath);
        }
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files == null || files.length == 0) {
            throw new IOException("No recipe files (*.txt) found in directory: " + filePath);
        }
    }

    public List<Recipe> loadRecipes() {
        try {
            return loadFromFile(recipesPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load recipes from: " + recipesPath, e);
        }
    }

    public String getRecipesPath() {
        return recipesPath;
    }
}