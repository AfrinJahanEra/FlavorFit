package src.FoodRecipes;

import java.io.IOException;
import java.util.List;
import src.FileManager.RecipeRepository;
import src.User.BaseFeature;
import src.Utils.ConsoleUI;

public class RecipeTracker extends BaseFeature {
    private final RecipeService service;

    public RecipeTracker(RecipeRepository repository) {
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
            "Search recipes by ingredient",
            "Browse all recipes"
        };

        Runnable[] handlers = {
            this::findRecipesByGoal,
            this::searchRecipesByIngredient,
            this::browseAllRecipes
        };

        displayMenuUntilExit(getTitle(), options, handlers);
    }

    private void findRecipesByGoal() {
        try {
            System.out.print("\nEnter your goal (e.g., weight loss, muscle gain): ");
            String goal = scanner.nextLine().trim();
            
            if (goal.isEmpty()) {
                System.out.println("Goal cannot be empty!");
                return;
            }
            
            List<Recipe> recipes = service.getRecipesByGoal(goal);
            displayRecipes(recipes);
        } catch (IOException e) {
            System.out.println("\nError loading recipes: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private void searchRecipesByIngredient() {
        try {
            System.out.print("\nEnter an ingredient to search: ");
            String ingredient = scanner.nextLine().trim();
            
            if (ingredient.isEmpty()) {
                System.out.println("Ingredient cannot be empty!");
                return;
            }
            
            List<Recipe> recipes = service.getRecipesByIngredient(ingredient);
            displayRecipes(recipes);
        } catch (IOException e) {
            System.out.println("\nError loading recipes: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private void browseAllRecipes() {
        try {
            List<Recipe> recipes = service.getAllRecipes();
            displayRecipes(recipes);
        } catch (IOException e) {
            System.out.println("\nError loading recipes: " + e.getMessage());
        }
    }

    private void displayRecipes(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("\nNo recipes found matching your criteria.");
            return;
        }

        System.out.println("\nFound " + recipes.size() + " recipe(s):");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }

        System.out.print("\nSelect a recipe number to view details (0 to go back): ");
        int choice = ConsoleUI.getIntInput("", 0, recipes.size());
        
        if (choice > 0) {
            System.out.println();
            recipes.get(choice - 1).display();
            System.out.println();
        }
    }
}