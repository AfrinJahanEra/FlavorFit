package src.Diet;

import java.time.Duration;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.*;
import src.Diet.PersistentFoodDatabase.FoodDatabaseException;

public class ConsoleUI {
    private final Scanner scanner;
    private final DayPlannerService plannerService;
    private final AlarmMonitor alarmMonitor;
    private final ReportGenerator reportGenerator;
    private final FoodLookup foodLookup;  // Only need lookup capability
    private final FoodWriter foodWriter;  // Separate writer for adding foods

    public ConsoleUI(DayPlannerService plannerService, 
                    AlarmMonitor alarmMonitor, 
                    FoodLookup foodLookup,
                    FoodWriter foodWriter) {
        this.scanner = new Scanner(System.in);
        this.plannerService = plannerService;
        this.alarmMonitor = alarmMonitor;
        this.reportGenerator = new EnhancedReportGenerator();
        this.foodLookup = foodLookup;
        this.foodWriter = foodWriter;
    }

    public void start() throws FoodDatabaseException {
        while (true) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            handleMenuChoice(choice);
        }
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add Food to Daily Log");
        System.out.println("2. Set Exercise Timer");
        System.out.println("3. Show Daily Report");
        System.out.println("4. Exit");
    }

    private void handleMenuChoice(int choice) throws FoodDatabaseException {
        switch (choice) {
            case 1:
                addFoodToDailyLog();
                break;
            case 2:
                setExerciseTimer();
                break;
            case 3:
                showDailyReport();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addFoodToDailyLog() throws PersistentFoodDatabase.FoodDatabaseException {
        System.out.print("Enter food name: ");
        String name = scanner.nextLine();
        
        Optional<NutritionalInfo> foodInfo = foodLookup.lookupFood(name);
        if (foodInfo.isPresent()) {
            System.out.println("\nFood Nutritional Information:");
            printNutritionalInfo(foodInfo.get());
            System.out.println("\nCurrent Daily Targets:");
            printDailyTargets();
            
            NutritionalInfo info = foodInfo.get();
            plannerService.addMeal(new Meal(name, info));
            
            checkNutritionTargets();
        } else {
            System.out.println("Food not found in database. Adding new food:");
            int calories = readInt("Enter calories: ");
            double protein = readDouble("Enter protein (g): ");
            double carbs = readDouble("Enter carbs (g): ");
            double fat = readDouble("Enter fat (g): ");
            
            NutritionalInfo info = new NutritionalInfo(calories, protein, carbs, fat);
            plannerService.addMeal(new Meal(name, info));
            foodWriter.addFood(name, info);  // Use FoodWriter to add new food
            checkNutritionTargets();
        }
    }

    private void printNutritionalInfo(NutritionalInfo info) {
        System.out.printf("Calories: %.1f | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg%n",
                (double)info.getCalories(), info.getProtein(), info.getCarbs(), info.getFat());
    }

    private void printDailyTargets() {
        DailyTargets targets = plannerService.getDailyTargets();
        System.out.printf("Calories: %d | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg%n",
                targets.getCalorieTarget(),
                targets.getMacroTargets().getProtein(),
                targets.getMacroTargets().getCarbs(),
                targets.getMacroTargets().getFat());
    }

    private void checkNutritionTargets() {
        NutritionSummary summary = plannerService.getCurrentNutritionSummary();
        DailyTargets targets = plannerService.getDailyTargets();
        
        System.out.println("\nCurrent Consumption vs Targets:");
        System.out.printf("Calories: %d/%d (%.1f%%)%n",
                summary.getTotalCalories(), targets.getCalorieTarget(),
                (double)summary.getTotalCalories() / targets.getCalorieTarget() * 100);
        System.out.printf("Protein: %.1f/%.1fg (%.1f%%)%n",
                summary.getTotalProtein(), targets.getMacroTargets().getProtein(),
                summary.getTotalProtein() / targets.getMacroTargets().getProtein() * 100);
        System.out.printf("Carbs: %.1f/%.1fg (%.1f%%)%n",
                summary.getTotalCarbs(), targets.getMacroTargets().getCarbs(),
                summary.getTotalCarbs() / targets.getMacroTargets().getCarbs() * 100);
        System.out.printf("Fat: %.1f/%.1fg (%.1f%%)%n",
                summary.getTotalFat(), targets.getMacroTargets().getFat(),
                summary.getTotalFat() / targets.getMacroTargets().getFat() * 100);
        
        if (summary.getTotalCalories() > targets.getCalorieTarget()) {
            System.out.println("\u001B[31mWarning: You've exceeded your calorie target!\u001B[0m");
        }
        if (summary.getTotalProtein() > targets.getMacroTargets().getProtein()) {
            System.out.println("\u001B[31mWarning: You've exceeded your protein target!\u001B[0m");
        }
        if (summary.getTotalCarbs() > targets.getMacroTargets().getCarbs()) {
            System.out.println("\u001B[31mWarning: You've exceeded your carbs target!\u001B[0m");
        }
        if (summary.getTotalFat() > targets.getMacroTargets().getFat()) {
            System.out.println("\u001B[31mWarning: You've exceeded your fat target!\u001B[0m");
        }
    }

    private void setExerciseTimer() {
        Duration targetDuration = plannerService.getDailyTargets().getExerciseTarget();
        System.out.printf("Your daily exercise target is: %d min %d sec%n",
                targetDuration.toMinutesPart(), targetDuration.toSecondsPart());
        
        int seconds = readInt("Enter countdown timer duration (seconds): ");
        
        if (seconds > targetDuration.getSeconds()) {
            System.out.printf("\u001B[33mYour target is %d min %d sec. Timer not started.%n",
                    targetDuration.toMinutesPart(), targetDuration.toSecondsPart());
            return;
        }
        
        System.out.println("Timer started. Don't interact until timer finishes!");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                for (int i = seconds; i > 0; i--) {
                    System.out.printf("\rTime remaining: %02d:%02d", i / 60, i % 60);
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println("\nTimer complete!");
                plannerService.startExerciseTimer(Duration.ofSeconds(seconds));
            } catch (InterruptedException e) {
                System.out.println("\nTimer interrupted!");
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Timer error: " + e.getMessage());
        }
        executor.shutdown();
    }

    private void showDailyReport() {
        DailySummary summary = plannerService.generateDailySummary(alarmMonitor);
        
        // Text report
        System.out.println("\n=== DAILY TEXT REPORT ===");
        reportGenerator.printSummary(summary);
        
        // Graphical report
        System.out.println("\n=== DAILY GRAPHICAL REPORT ===");
        reportGenerator.printGraphicalSummary(summary);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}