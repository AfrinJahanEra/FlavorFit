package src.Diet;


import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class DietFitnessPlanner {
    private final PlannerPersistence persistence;
    private final NotificationService notificationService;
    private final AlarmScheduler alarmScheduler;
    private final AlarmMonitor alarmMonitor;
    private final AlarmLifecycle alarmLifecycle;

    // Constructor to initialize dependencies
    public DietFitnessPlanner() {
        this.notificationService = new SoundNotificationService();
        this.alarmScheduler = new ScheduledAlertService(notificationService);
        this.alarmMonitor = (AlarmMonitor) alarmScheduler;
        this.alarmLifecycle = (AlarmLifecycle) alarmScheduler;
        this.persistence = new FileBasedPersistence("planner_state.ser");
    }

    // Instance-based start method
    public void start() {
        // Initialize services
        DayPlannerState state = persistence.load();

        // Initialize with default if no state exists
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

        // Restore state
        if (state.getMeals() != null) {
            state.getMeals().forEach(nutritionTracker::addMeal);
        }
        if (state.getExercises() != null) {
            state.getExercises().forEach(fitnessTracker::addExercise);
        }

        // Verify targets exist
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

        ConsoleUI ui = new ConsoleUI(plannerService, alarmMonitor);

        // Add shutdown hook to save state
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DayPlannerState newState = new DayPlannerState(
                targets,
                nutritionTracker.getMeals(),
                fitnessTracker.getExercises()
            );
            try {
                persistence.save(newState);
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
            int proteinTarget = readInt(scanner, "Enter protein target (g): ");
            int carbsTarget = readInt(scanner, "Enter carbs target (g): ");
            int fatTarget = readInt(scanner, "Enter fat target (g): ");
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
}