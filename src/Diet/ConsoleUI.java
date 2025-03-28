package src.Diet;


import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.*;

public class ConsoleUI {
    private final Scanner scanner;
    private final DayPlannerService plannerService;
    private final AlarmMonitor alarmMonitor;
    private final ReportGenerator reportGenerator;

    public ConsoleUI(DayPlannerService plannerService, AlarmMonitor alarmMonitor) {
        this.scanner = new Scanner(System.in);
        this.plannerService = plannerService;
        this.alarmMonitor = alarmMonitor;
        this.reportGenerator = new ReportGenerator();
    }

    public void start() {
        while (true) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            handleMenuChoice(choice);
        }
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Log Meal");
        System.out.println("2. Log Exercise");
        System.out.println("3. Start Exercise Timer");
        System.out.println("4. Show Summary");
        System.out.println("5. Show Graphical Report");
        System.out.println("6. Export Report");
        System.out.println("7. Exit");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                logMeal();
                break;
            case 2:
                logExercise();
                break;
            case 3:
                startExerciseTimer();
                break;
            case 4:
                showSummary();
                break;
            case 5:
                showGraphicalSummary();
                break;
            case 6:
                exportReport();
                break;
            case 7:
                System.exit(0);
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void logMeal() {
        System.out.print("Enter meal name: ");
        String name = scanner.nextLine();
        int calories = readInt("Enter calories: ");
        int protein = readInt("Enter protein (g): ");
        int carbs = readInt("Enter carbs (g): ");
        int fat = readInt("Enter fat (g): ");

        NutritionalInfo info = new NutritionalInfo(calories, protein, carbs, fat);
        plannerService.addMeal(new Meal(name, info));
        System.out.println("Meal logged successfully!");
    }

    private void logExercise() {
        System.out.print("Enter exercise name: ");
        String name = scanner.nextLine();
        int minutes = readInt("Enter duration (minutes): ");
        plannerService.addExercise(new Exercise(name, Duration.ofMinutes(minutes)));
        System.out.println("Exercise logged successfully!");
    }

    private void startExerciseTimer() {
        int seconds = readInt("Enter countdown timer duration (seconds): ");
        System.out.println("Timer started. Don't interact until timer finishes!");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                for (int i = seconds; i > 0; i--) {
                    System.out.printf("\rTime remaining: %02d:%02d", i / 60, i % 60);
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println("\nTimer complete!");
                NotificationService notification = new SoundNotificationService();
                notification.playSound();
            } catch (InterruptedException e) {
                System.out.println("\nTimer interrupted!");
            }
        });

        try {
            future.get(); // Block until timer completes
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private void showSummary() {
        reportGenerator.printSummary(plannerService.generateDailySummary(alarmMonitor));
    }

    private void showGraphicalSummary() {
        reportGenerator.printGraphicalSummary(plannerService.generateDailySummary(alarmMonitor));
    }

    private void exportReport() {
        try {
            reportGenerator.exportToCSV(
                plannerService.generateDailySummary(alarmMonitor),
                "daily_report.csv"
            );
            System.out.println("Report exported to daily_report.csv");
        } catch (IOException e) {
            System.out.println("Error exporting report");
        }
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
}