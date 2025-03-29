package src.User;

import java.util.List;
import src.DietPlanner.NutritionTrackerApp;
import src.Exercise.ExerciseMain;
import src.FoodRecipes.RecipeApp;
import src.FoodRecipes.RecipeRepository;
import src.HealthFactor.HealthFactorMain;
import src.Utils.ConsoleUI;
import src.VirtualNutritionist.VirtualNutritionist;

public class App {
    public static void main(String[] args){
        User user = collectUserInfo(); // Collect user information

        // Pass the User object to the Virtual Nutritionist
        VirtualNutritionist virtualNutritionist = new VirtualNutritionist(user);
        virtualNutritionist.start();

        // Pass the User object to the Health Factor Calculator
        HealthFactorMain healthFactorMain = new HealthFactorMain(user);
        healthFactorMain.start();

        // Pass the User object to the Exercise Recommendation System
        ExerciseMain exerciseMain = new ExerciseMain(user);
        exerciseMain.start();

        // Initialize and start the Recipe App
        RecipeRepository repository = new RecipeRepository("src\\recipes");
        RecipeApp recipeApp = new RecipeApp(repository);
        recipeApp.start();

        System.out.println("\nStarting Nutrition Tracker...");
        NutritionTrackerApp.start();

        // Initialize and start the Diet Fitness Planner
    }

    private static User collectUserInfo() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("USER REGISTRATION");

        String name = ConsoleUI.getStringInput("Enter your name: ");
        int age = ConsoleUI.getIntInput("Enter your age: ", 1, 120);
        String gender = ConsoleUI.getStringInput("Enter your gender (M/F): ").toUpperCase();
        double weight = ConsoleUI.getDoubleInput("Enter your weight (kg): ");
        double height = ConsoleUI.getDoubleInput("Enter your height (m): ");

        List<String> medicalConditions = ConsoleUI.getCommaSeparatedInput(
            "Enter medical conditions (comma-separated): ");
        List<String> allergies = ConsoleUI.getCommaSeparatedInput(
            "Enter allergies (comma-separated): ");
        String activityLevel = ConsoleUI.getStringInput(
            "Enter activity level (low/medium/high): ");

        return new User(name, age, gender, weight, height,
                      medicalConditions, allergies, activityLevel);
    }
}