// package src.VirtualNutritionist;

// import java.io.IOException;
// import java.util.List;
// import src.User.BaseFeature;
// import src.User.User;

// public class VirtualNutritionist extends BaseFeature {
//     private final User user;
//     private final MealService mealService;
//     private final TipService tipService;
//     private final QnAService qnaService;

//     public VirtualNutritionist(User user) throws IOException {
//         if (user == null) {
//             throw new IllegalArgumentException("User cannot be null");
//         }
//         this.user = user;

//         try {
//             this.mealService = new MealService("src/VirtualNutritionist/meals.txt");
//             this.tipService = new TipService("src/VirtualNutritionist/tips.txt");
//             this.qnaService = new QnAService();
//         } catch (IOException e) {
//             throw new IOException("Failed to initialize VirtualNutritionist services", e);
//         }
//     }

//     @Override
//     public String getTitle() {
//         return "Virtual Nutritionist";
//     }

//     @Override
//     public void display() {
//         String[] options = {
//             "View Personalized Meal Plan",
//             "Get Health Tips",
//             "Ask a Nutrition Question",
//             "Exit"
//         };

//         Runnable[] handlers = {
//             this::showMealPlan,
//             this::showHealthTips,
//             this::askNutritionQuestion,
//             () -> {} // Empty handler for exit
//         };

//         displayMenuUntilExit(getTitle(), options, handlers);
//     }

//     private void showMealPlan() {
//         System.out.println("\n--- Personalized Meal Plan ---");
//         List<Meal> meals = mealService.getFilteredMeals(
//             "breakfast", 
//             user.getMedicalConditions(), 
//             user.getAllergies()
//         );

//         if (meals.isEmpty()) {
//             System.out.println("No suitable meals found for your profile.");
//         } else {
//             meals.forEach(meal -> System.out.println("• " + meal.getName()));
//         }
//         System.out.println();
//     }

//     private void showHealthTips() {
//         System.out.println("\n--- Personalized Tips ---");
//         List<String> conditions = user.getMedicalConditions();

//         if (conditions == null || conditions.isEmpty()) {
//             System.out.println("No medical conditions specified in your profile.");
//             return;
//         }

//         conditions.forEach(condition -> {
//             System.out.println("\nFor " + condition + ":");
//             List<String> tips = tipService.getTipsForCondition(condition);

//             if (tips.isEmpty()) {
//                 System.out.println("No tips available for this condition.");
//             } else {
//                 tips.forEach(tip -> System.out.println("• " + tip));
//             }
//         });
//         System.out.println();
//     }

//     private void askNutritionQuestion() {
//         System.out.print("\nEnter your nutrition question: ");
//         String question = scanner.nextLine();

//         if (question == null || question.trim().isEmpty()) {
//             System.out.println("Question cannot be empty.");
//             return;
//         }

//         System.out.println("\n--- Response ---");
//         String response = qnaService.getResponse(question, user);
//         System.out.println(response);
//         System.out.println();
//     }
// }

package src.VirtualNutritionist;

import java.io.IOException;
import java.util.List;
import src.User.BaseFeature;
import src.User.User;

public class VirtualNutritionist extends BaseFeature {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";

    private final User user;
    private final MealService mealService;
    private final TipService tipService;
    private final QnAService qnaService;

    public VirtualNutritionist(User user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;

        try {
            this.mealService = new MealService("src/VirtualNutritionist/meals.txt");
            this.tipService = new TipService("src/VirtualNutritionist/tips.txt");
            this.qnaService = new QnAService();
        } catch (IOException e) {
            throw new IOException("Failed to initialize VirtualNutritionist services", e);
        }
    }

    @Override
    public String getTitle() {
        return "Virtual Nutritionist";
    }

    @Override
    public void display() {
        String[] options = {
                "View Personalized Meal Plan & Health Tips",
                "Ask a Nutrition Question",
        };

        Runnable[] handlers = {
                this::showMealPlanAndTips,
                this::askNutritionQuestion,
                // () -> {
                // } // Empty handler for exit
        };

        displayMenuUntilExit(getTitle(), options, handlers);
    }

    private void showMealPlanAndTips() {
        showMealPlan();
        showHealthTips();
    }

    private void showMealPlan() {

        System.out.println(YELLOW + BOLD + "\n═════ Personalized Meal Plan ═════" + RESET);

   
        List<Meal> meals = mealService.getFilteredMeals(
                "breakfast",
                user.getMedicalConditions(),
                user.getAllergies());

        if (meals.isEmpty()) {
            System.out.println("No suitable meals found for your profile.");
        } else {
            meals.forEach(meal -> System.out.println("->  " + meal.getName()));
        }
    }

    private void showHealthTips() {

        System.out.println(YELLOW + BOLD + "\n═════ Personalized Health Tips ═════" + RESET);

        List<String> conditions = user.getMedicalConditions();

        if (conditions == null || conditions.isEmpty()) {
            System.out.println("No medical conditions specified in your profile.");
            return;
        }

        conditions.forEach(condition -> {
            System.out.println("\nFor " + condition + ":");
            List<String> tips = tipService.getTipsForCondition(condition);

            if (tips.isEmpty()) {
                System.out.println("No tips available for this condition.");
            } else {
                tips.forEach(tip -> System.out.println("->  " + tip));
            }
        });
        System.out.println();
    }

    private void askNutritionQuestion() {
        System.out.print("\nEnter your nutrition question: ");
        String question = scanner.nextLine();

        if (question == null || question.trim().isEmpty()) {
            System.out.println("Question cannot be empty.");
            return;
        }

        System.out.println(YELLOW + BOLD + "\n════════ RESPONSE ════════" + RESET);
        String response = qnaService.getResponse(question, user);
        System.out.println(response);
        System.out.println();
    }
}