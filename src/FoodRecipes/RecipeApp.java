package src.FoodRecipes;

import java.util.List;
import java.util.Scanner;

public class RecipeApp {
    public static void main(String[] args) {
        RecipeRepository repository = new RecipeRepository("src\\recipes");
        RecipeService service = new RecipeService(repository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to Recipe App!");
            System.out.println("1. Find recipes by goal");
            System.out.println("2. Search recipes by ingredient");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 3) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter your goal (e.g., weight loss, muscle gain): ");
                    String goal = scanner.nextLine();
                    List<Recipe> goalRecipes = service.getRecipesByGoal(goal);
                    displayRecipes(goalRecipes, scanner);
                    break;

                case 2:
                    System.out.print("Enter an ingredient to search: ");
                    String ingredient = scanner.nextLine();
                    List<Recipe> ingredientRecipes = service.getRecipesByIngredient(ingredient);
                    displayRecipes(ingredientRecipes, scanner);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using Recipe App!");
    }

    private static void displayRecipes(List<Recipe> recipes, Scanner scanner) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }

        System.out.println("Recipes found:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }

        System.out.print("Select a recipe number to view details (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= recipes.size()) {
            recipes.get(choice - 1).display();
        }
    }
}
