package src.Diet;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import src.Diet.PersistentFoodDatabase.FoodDatabaseException;

public class DietFitnessPlanner {
    private final PlannerPersistence persistence;
    private final NotificationService notificationService;
    private final AlarmScheduler alarmScheduler;
    private final AlarmMonitor alarmMonitor;
    private final AlarmLifecycle alarmLifecycle;
    private PersistentFoodDatabase foodDatabase; // Single source of truth for food data

    public DietFitnessPlanner() {
        this.notificationService = new SoundNotificationService();
        this.alarmScheduler = new ScheduledAlertService(notificationService);
        this.alarmMonitor = (AlarmMonitor) alarmScheduler;
        this.alarmLifecycle = (AlarmLifecycle) alarmScheduler;
        this.persistence = new FileBasedPersistence("planner_state.ser");
        
        try {
            this.foodDatabase = new PersistentFoodDatabase(
                "food_data.ser",
                "default_foods.txt"
            );
        } catch (PersistentFoodDatabase.FoodDatabaseException e) {
            System.err.println("FATAL: Could not initialize food database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Exit if we can't initialize the food database
        }
    }

    public void start() throws FoodDatabaseException {
        DayPlannerState state = persistence.load();

        if (state == null) {
            System.out.println("No saved state found. Creating new planner.");
            state = initializeNewPlanner();
            if (state == null) {
                System.err.println("Failed to initialize new planner. Exiting.");
                return;
            }
        }

        InMemoryNutritionTracker nutritionTracker = new InMemoryNutritionTracker();
        InMemoryFitnessTracker fitnessTracker = new InMemoryFitnessTracker();

        if (state.getMeals() != null) {
            state.getMeals().forEach(nutritionTracker::addMeal);
        }
        if (state.getExercises() != null) {
            state.getExercises().forEach(fitnessTracker::addExercise);
        }

        DailyTargets targets = state.getTargets();
        if (targets == null) {
            System.err.println("No targets found in state. Exiting.");
            return;
        }

        DayPlannerService plannerService = new DayPlannerService(
            targets,
            nutritionTracker,
            nutritionTracker,
            fitnessTracker,
            fitnessTracker,
            alarmScheduler
        );

        ConsoleUI ui = new ConsoleUI(
            plannerService,
            alarmMonitor,
            foodDatabase, // Passed as FoodLookup
            foodDatabase  // Passed as FoodWriter
        );

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DayPlannerState newState = new DayPlannerState(
                targets,
                nutritionTracker.getMeals(),
                fitnessTracker.getExercises()
            );
            try {
                persistence.save(newState);
                foodDatabase.saveToFile(); // Using the persistence capability
            } catch (Exception e) {
                System.err.println("Failed to save state: " + e.getMessage());
            }
            alarmLifecycle.shutdown();
        }));

        ui.start();
    }

    private DayPlannerState initializeNewPlanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Setting up new daily targets:");

        try {
            int calorieTarget = readInt(scanner, "Enter calorie target: ");
            double proteinTarget = readDouble(scanner, "Enter protein target (g): ");
            double carbsTarget = readDouble(scanner, "Enter carbs target (g): ");
            double fatTarget = readDouble(scanner, "Enter fat target (g): ");
            int exerciseMinutes = readInt(scanner, "Enter exercise target (minutes): ");

            NutritionalInfo macroTargets = new NutritionalInfo(0, proteinTarget, carbsTarget, fatTarget);
            DailyTargets targets = new DailyTargets(
                calorieTarget,
                macroTargets,
                Duration.ofMinutes(exerciseMinutes)
            );

            return new DayPlannerState(targets, List.of(), List.of());
        } catch (Exception e) {
            System.err.println("Error initializing planner: " + e.getMessage());
            return null;
        }
    }

    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private double readDouble(Scanner scanner, String prompt) {
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