package src.FoodRecipes;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getRecipesByGoal(String goal) {
        return repository.loadRecipes().stream()
                .filter(recipe -> recipe.getGoal().equalsIgnoreCase(goal))
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByIngredient(String ingredient) {
        return repository.loadRecipes().stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .anyMatch(i -> i.toLowerCase().contains(ingredient.toLowerCase())))
                .collect(Collectors.toList());
    }
}
