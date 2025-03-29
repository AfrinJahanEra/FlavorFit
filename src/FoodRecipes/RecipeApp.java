package src.FoodRecipes;

import java.util.List;
import src.User.BaseFeature;

public class RecipeApp extends BaseFeature {
    private final RecipeService service;

    public RecipeApp(RecipeRepository repository) {
        this.service = new RecipeService(repository);
    }

    @Override
    public String getTitle() {
        return "Recipe Finder";
    }

    @Override
    public void display() {
        String[] options = {
            "Find recipes by goal",
            "Search recipes by ingredient"
        };

        Runnable[] handlers = {
            () -> findRecipesByGoal(),
            () -> searchRecipesByIngredient()
        };

        displayMenu(getTitle(), options, handlers);
    }

    private void findRecipesByGoal() {
        System.out.print("Enter your goal (e.g., weight loss, muscle gain): ");
        String goal = scanner.nextLine();
        List<Recipe> recipes = service.getRecipesByGoal(goal);
        displayRecipes(recipes);
    }

    private void searchRecipesByIngredient() {
        System.out.print("Enter an ingredient to search: ");
        String ingredient = scanner.nextLine();
        List<Recipe> recipes = service.getRecipesByIngredient(ingredient);
        displayRecipes(recipes);
    }

    private void displayRecipes(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }

        System.out.println("Recipes found:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }

        System.out.print("Select a recipe number to view details (or 0 to go back): ");
        int choice = getIntInput("", 0, recipes.size());
        
        if (choice > 0) {
            recipes.get(choice - 1).display();
        }
    }
}