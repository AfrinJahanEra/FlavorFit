package src.VirtualNutritionist;

import java.io.IOException;
import src.User.BaseFeature;
import src.User.User;

public class VirtualNutritionist extends BaseFeature {
    private final User user;
    private final MealService mealService;
    private final TipService tipService;
    private final QnAService qnaService;

    public VirtualNutritionist(User user) throws IOException {
        this.user = user;
        this.mealService = new MealService("src/VirtualNutritionist/meals.txt");
        this.tipService = new TipService("src/VirtualNutritionist/tips.txt");
        this.qnaService = new QnAService();
    }

    @Override
    public String getTitle() {
        return "Virtual Nutritionist";
    }

    @Override
    public void display() {
        String[] options = {
            "View Personalized Meal Plan",
            "Get Health Tips",
            "Ask a Nutrition Question"
        };

        Runnable[] handlers = {
            () -> showMealPlan(),
            () -> showHealthTips(),
            () -> askNutritionQuestion()
        };

        displayMenuUntilExit(getTitle(), options, handlers);
    }

    private void showMealPlan() {
        System.out.println("\n--- Personalized Meal Plan ---");
        mealService.getFilteredMeals("breakfast", user.getMedicalConditions(), user.getAllergies())
                 .forEach(meal -> System.out.println("• " + meal.getName()));
        System.out.println();
    }

    private void showHealthTips() {
        System.out.println("\n--- Personalized Tips ---");
        user.getMedicalConditions().forEach(condition -> {
            System.out.println("\nFor " + condition + ":");
            tipService.getTipsForCondition(condition).forEach(tip -> 
                System.out.println("• " + tip)
            );
        });
        System.out.println();
    }

    private void askNutritionQuestion() {
        System.out.print("\nEnter your nutrition question: ");
        String question = scanner.nextLine();
        System.out.println("\nResponse: " + qnaService.getResponse(question));
        System.out.println();
    }
}