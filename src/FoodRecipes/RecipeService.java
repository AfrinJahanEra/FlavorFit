package src.FoodRecipes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import src.FileManager.RecipeRepository;

public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getRecipesByGoal(String goal) throws IOException {
        if (goal == null || goal.trim().isEmpty()) {
            throw new IllegalArgumentException("Goal cannot be null or empty");
        }
        String searchGoal = goal.trim().toLowerCase();
        
        return repository.loadFromFile(repository.getRecipesPath()).stream()
                .filter(recipe -> recipe.getGoal().trim().toLowerCase().equals(searchGoal))
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByIngredient(String ingredient) throws IOException {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient cannot be null or empty");
        }
        String searchIngredient = ingredient.trim().toLowerCase();
        
        return repository.loadFromFile(repository.getRecipesPath()).stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .anyMatch(i -> i.trim().toLowerCase().contains(searchIngredient)))
                .collect(Collectors.toList());
    }

    public List<Recipe> getAllRecipes() throws IOException {
        return repository.loadFromFile(repository.getRecipesPath());
    }
}