package src.VirtualNutritionist;

import java.util.Scanner;
import src.User.User;

public class VirtualNutritionist {
    private final User user;

    // Constructor to accept the User object
    public VirtualNutritionist(User user) {
        this.user = user;
    }

    public void start() {
        try {
            MealService mealService = new MealService("src\\VirtualNutritionist\\meals.txt");
            TipService tipService = new TipService("src\\VirtualNutritionist\\tips.txt");
            QnAService qnaService = new QnAService();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the Virtual Nutritionist!");

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