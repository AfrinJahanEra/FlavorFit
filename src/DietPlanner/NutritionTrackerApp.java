package src.DietPlanner;

import java.util.concurrent.TimeUnit;
import src.User.BaseFeature;
import src.Utils.ConsoleUI;

public class NutritionTrackerApp extends BaseFeature {
    private final UserProfile userProfile;
    private final FoodRepository foodRepository;

    public NutritionTrackerApp() {
        NutritionDataManager dataManager = new NutritionDataManager();
        this.userProfile = new UserProfile(dataManager);
        this.foodRepository = dataManager;
        userProfile.loadData();
    }

    @Override
    public String getTitle() {
        return "Nutrition Tracker";
    }

    @Override
    public void display() {
        String[] options = {
            "Set Targets",
            "Add Food",
            "Start Exercise Timer",
            "Show Report"
        };

        Runnable[] handlers = {
            () -> setTargets(),
            () -> addFood(),
            () -> startExerciseTimer(),
            () -> showReport()
        };

        displayMenuUntilExit(getTitle(), options, handlers);
    }

    private void setTargets() {
        System.out.print("Enter calorie target: ");
        int calories = scanner.nextInt();
        System.out.print("Enter protein target (g): ");
        double protein = scanner.nextDouble();
        System.out.print("Enter carbs target (g): ");
        double carbs = scanner.nextDouble();
        System.out.print("Enter fat target (g): ");
        double fat = scanner.nextDouble();
        System.out.print("Enter exercise target (minutes): ");
        int exercise = scanner.nextInt();
        scanner.nextLine();

        userProfile.updateTargets(calories, protein, carbs, fat, exercise);
        System.out.println("Targets set successfully!");
    }

    private void addFood() {
        System.out.print("Enter food name: ");
        String foodName = scanner.nextLine();

        foodRepository.findFood(foodName).ifPresentOrElse(
            food -> {
                displayFoodInfo(food);
                userProfile.addFoodConsumption(food);
                displayCurrentTotals();
                checkTargets();
            },
            () -> System.out.println("Food not found in database.")
        );
    }

    private void checkTargets() {
        UserTarget target = userProfile.getUserTarget();
        UserProgress progress = userProfile.getUserProgress();

        boolean exceeded = false;
        if (progress.getCalories() > target.getCalorieTarget()) {
            System.out.println("Warning: You have exceeded your calorie target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        // ... similar checks for protein, carbs, fat ...

        if (!exceeded) {
            System.out.println("You're within your targets. Good job!");
        }
    }

    private void displayFoodInfo(Food food) {
        System.out.println("\nNutrition Information for " + food.getName() + ":");
        System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
                food.getCalories(), food.getProtein(), food.getCarbs(), food.getFat());
    }

    private void displayCurrentTotals() {
        UserProgress progress = userProfile.getUserProgress();
        System.out.println("\nYour Current Totals:");
        System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
                progress.getCalories(), progress.getProtein(),
                progress.getCarbs(), progress.getFat());
    }

    private void startExerciseTimer() {
        UserProgress progress = userProfile.getUserProgress();
        UserTarget target = userProfile.getUserTarget();

        if (progress.getExerciseTime() >= target.getExerciseTarget()) {
            handleExceededExerciseTarget();
            return;
        }

        System.out.print("Enter exercise duration (minutes): ");
        int minutes = ConsoleUI.getIntInput("", 1, Integer.MAX_VALUE);

        runExerciseTimer(minutes);
        userProfile.addExerciseTime(minutes);
        System.out.println("Total exercise time: " + progress.getExerciseTime() + " minutes");
    }

    private void runExerciseTimer(int minutes) {
        System.out.println("Starting exercise timer for " + minutes + " minutes...");
        try {
            for (int i = minutes * 60; i > 0; i--) {
                if (i % 60 == 0) {
                    System.out.println("Time remaining: " + (i / 60) + " minutes");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Time's up! Exercise completed.");
            SoundPlayer.playSound();
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }

    private void showReport() {
        NutritionReport report = userProfile.generateReport();
        report.display();
    }

    private void handleExceededExerciseTarget() {
        System.out.println("You've already reached your exercise target!");
        System.out.print("Do you want to increase your exercise target? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("yes")) {
            System.out.print("Enter new exercise target (minutes): ");
            int newTarget = ConsoleUI.getIntInput("", 1, Integer.MAX_VALUE);
            userProfile.getUserTarget().setExerciseTarget(newTarget);
            userProfile.saveTargets();
            System.out.println("Exercise target updated successfully!");
            SoundPlayer.playSound();
        }
        showReport();
    }
}