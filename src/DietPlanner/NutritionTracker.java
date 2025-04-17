package src.DietPlanner;

import java.util.concurrent.TimeUnit;
import src.User.BaseFeature;
import src.Utils.ConsoleUI;

public class NutritionTracker extends BaseFeature {
    private final UserProfile userProfile;
    private final NutritionDataManager nutritionDataManager;

    public NutritionTracker() {
        this.nutritionDataManager = new NutritionDataManager();
        this.userProfile = new UserProfile(nutritionDataManager);
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
            this::setTargets,
            this::addFood,
            this::startExerciseTimer,
            this::showReport
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

        nutritionDataManager.findFood(foodName).ifPresentOrElse(
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

        if (progress.getProtein() > target.getProteinTarget()) {
            System.out.println("Warning: You have exceeded your protein target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        if (progress.getCarbs() > target.getCarbsTarget()) {
            System.out.println("Warning: You have exceeded your carbs target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        if (progress.getFat() > target.getFatTarget()) {
            System.out.println("Warning: You have exceeded your fat target!");
            SoundPlayer.playSound();
            exceeded = true;
        }

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
                // Clear the previous line
                System.out.print("\r");
                
                // Calculate minutes and seconds remaining
                int mins = i / 60;
                int secs = i % 60;
                
                // Format the time string with ANSI codes for bold yellow
                String timeString = String.format("\u001B[1;33mTime remaining: %02d:%02d\u001B[0m", mins, secs);
                System.out.print(timeString);
                
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("\nTime's up! Exercise completed.");
            SoundPlayer.playSound();
        } catch (InterruptedException e) {
            System.out.println("\nTimer interrupted.");
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
            nutritionDataManager.saveTargets(userProfile.getUserTarget());
            System.out.println("Exercise target updated successfully!");
        }
        showReport();
    }
}