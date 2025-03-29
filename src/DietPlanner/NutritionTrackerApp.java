package src.DietPlanner;

// import java.util.Scanner;
// import java.util.concurrent.TimeUnit;

// public class NutritionTrackerApp {
//     private final UserProfile userProfile;
//     private final FoodRepository foodRepository;
//     private final Scanner scanner;

//     public NutritionTrackerApp() {
//         NutritionDataManager dataManager = new NutritionDataManager();
//         this.userProfile = new UserProfile(dataManager);
//         this.foodRepository = dataManager;
//         this.scanner = new Scanner(System.in);
//     }

//     public void start() {
//         userProfile.loadData(); // Load user profile data
//         displayMainMenu(); // Display the main menu
//     }

//     private void displayMainMenu() {
//         while (true) {
//             System.out.println("\n--- Nutrition Tracker ---");
//             System.out.println("1. Set Targets");
//             System.out.println("2. Add Food");
//             System.out.println("3. Start Exercise Timer");
//             System.out.println("4. Show Report");
//             System.out.println("5. Exit");
//             System.out.print("Choose an option: ");

//             int choice = scanner.nextInt();
//             scanner.nextLine();

//             switch (choice) {
//                 case 1 -> setTargets();
//                 case 2 -> addFood();
//                 case 3 -> startExerciseTimer();
//                 case 4 -> showReport();
//                 case 5 -> {
//                     System.out.println("Exiting...");
//                     return;
//                 }
//                 default -> System.out.println("Invalid option. Try again.");
//             }
//         }
//     }

//     private void setTargets() {
//         System.out.print("Enter calorie target: ");
//         int calories = scanner.nextInt();
//         System.out.print("Enter protein target (g): ");
//         double protein = scanner.nextDouble();
//         System.out.print("Enter carbs target (g): ");
//         double carbs = scanner.nextDouble();
//         System.out.print("Enter fat target (g): ");
//         double fat = scanner.nextDouble();
//         System.out.print("Enter exercise target (minutes): ");
//         int exercise = scanner.nextInt();
//         scanner.nextLine();

//         userProfile.updateTargets(calories, protein, carbs, fat, exercise);
//         System.out.println("Targets set successfully!");
//     }

//     private void addFood() {
//         System.out.print("Enter food name: ");
//         String foodName = scanner.nextLine();

//         foodRepository.findFood(foodName).ifPresentOrElse(
//                 food -> {
//                     displayFoodInfo(food);
//                     userProfile.addFoodConsumption(food);
//                     displayCurrentTotals();
//                     checkTargets();
//                 },
//                 () -> System.out.println("Food not found in database."));
//     }

//     private void checkTargets() {
//         UserTarget target = userProfile.getUserTarget();
//         UserProgress progress = userProfile.getUserProgress();

//         boolean exceeded = false;
//         if (progress.getCalories() > target.getCalorieTarget()) {
//             System.out.println("Warning: You have exceeded your calorie target!");
//             SoundPlayer.playSound();
//             exceeded = true;
//         }
//         if (progress.getProtein() > target.getProteinTarget()) {
//             System.out.println("Warning: You have exceeded your protein target!");
//             SoundPlayer.playSound();
//             exceeded = true;
//         }
//         if (progress.getCarbs() > target.getCarbsTarget()) {
//             System.out.println("Warning: You have exceeded your carbs target!");
//             SoundPlayer.playSound();
//             exceeded = true;
//         }
//         if (progress.getFat() > target.getFatTarget()) {
//             System.out.println("Warning: You have exceeded your fat target!");
//             SoundPlayer.playSound();
//             exceeded = true;
//         }

//         if (!exceeded) {
//             System.out.println("You're within your targets. Good job!");
//         }
//     }

//     private void displayFoodInfo(Food food) {
//         System.out.println("\nNutrition Information for " + food.getName() + ":");
//         System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
//                 food.getCalories(), food.getProtein(), food.getCarbs(), food.getFat());
//     }

//     private void displayCurrentTotals() {
//         UserProgress progress = userProfile.getUserProgress();
//         System.out.println("\nYour Current Totals:");
//         System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
//                 progress.getCalories(), progress.getProtein(),
//                 progress.getCarbs(), progress.getFat());
//     }

//     private void startExerciseTimer() {
//         UserProgress progress = userProfile.getUserProgress();
//         UserTarget target = userProfile.getUserTarget();

//         if (progress.getExerciseTime() >= target.getExerciseTarget()) {
//             handleExceededExerciseTarget();
//             return;
//         }

//         System.out.print("Enter exercise duration (minutes): ");
//         int minutes = scanner.nextInt();
//         scanner.nextLine();

//         if (minutes <= 0) {
//             System.out.println("Invalid duration. Timer not started.");
//             return;
//         }

//         runExerciseTimer(minutes);
//         userProfile.addExerciseTime(minutes);
//         System.out.println("Total exercise time: " + progress.getExerciseTime() + " minutes");
//     }

//     private void runExerciseTimer(int minutes) {
//         System.out.println("Starting exercise timer for " + minutes + " minutes...");
//         try {
//             for (int i = minutes * 60; i > 0; i--) {
//                 if (i % 60 == 0) {
//                     System.out.println("Time remaining: " + (i / 60) + " minutes");
//                 }
//                 TimeUnit.SECONDS.sleep(1);
//             }
//             System.out.println("Time's up! Exercise completed.");
//             SoundPlayer.playSound();
//         } catch (InterruptedException e) {
//             System.out.println("Timer interrupted.");
//         }
//     }

//     private void showReport() {
//         NutritionReport report = userProfile.generateReport();
//         report.display();
//     }

//     private void handleExceededExerciseTarget() {
//         System.out.println("You've already reached your exercise target!");
//         System.out.print("Do you want to increase your exercise target? (yes/no): ");
//         String response = scanner.nextLine().toLowerCase();

//         if (response.equals("yes")) {
//             System.out.print("Enter new exercise target (minutes): ");
//             int newTarget = scanner.nextInt();
//             scanner.nextLine();
//             userProfile.getUserTarget().setExerciseTarget(newTarget);
//             userProfile.saveTargets();
//             System.out.println("Exercise target updated successfully!");
//             SoundPlayer.playSound();
//         }
//         showReport();
//     }

// }


import java.util.concurrent.TimeUnit;
import src.User.BaseFeature;

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

        displayMenu(getTitle(), options, handlers);
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
        int minutes = getIntInput("", 1, Integer.MAX_VALUE);

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
            int newTarget = getIntInput("", 1, Integer.MAX_VALUE);
            userProfile.getUserTarget().setExerciseTarget(newTarget);
            userProfile.saveTargets();
            System.out.println("Exercise target updated successfully!");
            SoundPlayer.playSound();
        }
        showReport();
    }
}