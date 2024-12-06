package src.FoodRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private final String recipesPath;

    public RecipeRepository(String recipesPath) {
        this.recipesPath = recipesPath;
    }

    public List<Recipe> loadRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        File folder = new File(recipesPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new RuntimeException("Invalid recipes directory.");
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String name = br.readLine();
                    String goal = br.readLine();
                    List<String> ingredients = new ArrayList<>();
                    List<String> steps = new ArrayList<>();

                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals("---")) break;
                        ingredients.add(line);
                    }
                    while ((line = br.readLine()) != null) {
                        steps.add(line);
                    }

                    recipes.add(new Recipe(name, goal, ingredients, steps));
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getName());
                }
            }
        }
        return recipes;
    }
}
