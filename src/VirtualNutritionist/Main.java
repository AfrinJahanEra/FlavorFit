package src.VirtualNutritionist;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            MealService mealService = new MealService("src\\VirtualNutritionist\\meals.txt");
            TipService tipService = new TipService("src\\VirtualNutritionist\\tips.txt");
            QnAService qnaService = new QnAService();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the Virtual Nutritionist!");
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline
            System.out.print("Enter your gender: ");
            String gender = scanner.nextLine();
            System.out.print("Enter your weight: ");
            double weight = scanner.nextDouble();
            System.out.print("Enter your height: ");
            double height = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter your medical conditions (comma-separated): ");
            List<String> medicalConditions = Arrays.asList(scanner.nextLine().split(","));
            System.out.print("Enter your allergies (comma-separated): ");
            List<String> allergies = Arrays.asList(scanner.nextLine().split(","));
            System.out.print("Enter your activity level (e.g., low, medium, high): ");
            String activityLevel = scanner.nextLine();
            User user = new User(name, age, gender, weight, height, medicalConditions, allergies, activityLevel);
            System.out.println("\n--- Personalized Meal Plan ---");
            mealService.getFilteredMeals("breakfast", user.getMedicalConditions(), user.getAllergies())
                    .forEach(meal -> System.out.println("Meal: " + meal.getName()));
            System.out.println("\n--- Personalized Tips ---");
            user.getMedicalConditions().forEach(condition -> 
                tipService.getTipsForCondition(condition).forEach(System.out::println)
            );
            System.out.println("\n--- Ask a question ---");
            String question = scanner.nextLine();
            System.out.println("Response: " + qnaService.getResponse(question));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
