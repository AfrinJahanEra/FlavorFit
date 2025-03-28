package src.Diet;


import java.io.*;
import java.io.*;
import java.time.Duration;
import java.util.List;

public class ReportGenerator {
    public void printSummary(DailySummary summary) {
        System.out.println("\n=== DAILY SUMMARY ===");
        printNutritionSummary(summary);
        printExerciseSummary(summary);
        printAlarms(summary);
    }

    public void printGraphicalSummary(DailySummary summary) {
        System.out.println("\n=== GRAPHICAL DAILY REPORT ===");
        printHorizontalGraphs(summary);
        printVerticalGraphs(summary);
        printAlarmStatus(summary);
    }

    private void printNutritionSummary(DailySummary summary) {
        NutritionSummary nutrition = summary.getNutrition();
        NutritionalInfo targets = summary.getTargets().getMacroTargets();
        
        System.out.println("\nNutrition Tracking:");
        System.out.printf("Calories: %d/%d%n", nutrition.getTotalCalories(), summary.getTargets().getCalorieTarget());
        System.out.printf("Protein: %dg/%dg%n", nutrition.getTotalProtein(), targets.getProtein());
        System.out.printf("Carbs: %dg/%dg%n", nutrition.getTotalCarbs(), targets.getCarbs());
        System.out.printf("Fat: %dg/%dg%n", nutrition.getTotalFat(), targets.getFat());
    }

    private void printExerciseSummary(DailySummary summary) {
        System.out.println("\nExercise Tracking:");
        System.out.printf("Total Exercise Time: %s/%s%n",
            formatDuration(summary.getFitness().getTotalExerciseTime()),
            formatDuration(summary.getTargets().getExerciseTarget()));
    }

    private void printAlarms(DailySummary summary) {
        System.out.println("\nTriggered Alarms:");
        summary.getAlarms().forEach(System.out::println);
    }

    private String formatDuration(Duration duration) {
        return String.format("%02d:%02d", duration.toHours(), duration.toMinutesPart());
    }

    private void printHorizontalGraphs(DailySummary summary) {
        System.out.println("\nHORIZONTAL PROGRESS:");
        printCalorieGraph(summary);
        printExerciseGraph(summary);
    }

    private void printCalorieGraph(DailySummary summary) {
        int current = summary.getNutrition().getTotalCalories();
        int target = summary.getTargets().getCalorieTarget();
        System.out.println("Calories:");
        System.out.println(getHorizontalBar(current, target, 40, "kcal"));
    }

    private void printExerciseGraph(DailySummary summary) {
        long current = summary.getFitness().getTotalExerciseTime().toMinutes();
        long target = summary.getTargets().getExerciseTarget().toMinutes();
        System.out.println("\nExercise:");
        System.out.println(getHorizontalBar((int) current, (int) target, 40, "minutes"));
    }

    private void printVerticalGraphs(DailySummary summary) {
        System.out.println("\nVERTICAL MACRONUTRIENT ANALYSIS:");
        printMacroVerticalBars(summary);
    }

    private void printMacroVerticalBars(DailySummary summary) {
        NutritionSummary nutrition = summary.getNutrition();
        NutritionalInfo targets = summary.getTargets().getMacroTargets();

        System.out.println(getVerticalBar(nutrition.getTotalProtein(), targets.getProtein(), "Protein \u001B[34m"));
        System.out.println(getVerticalBar(nutrition.getTotalCarbs(), targets.getCarbs(), "Carbs   \u001B[32m"));
        System.out.println(getVerticalBar(nutrition.getTotalFat(), targets.getFat(), "Fat     \u001B[33m"));
    }

    private void printAlarmStatus(DailySummary summary) {
        System.out.println("\nALERT STATUS:");
        if (summary.getAlarms().isEmpty()) {
            System.out.println("\u001B[32m✓ All targets within safe limits\u001B[0m");
        } else {
            summary.getAlarms().forEach(alert -> System.out.println("\u001B[31m⚠ " + alert + "\u001B[0m"));
        }
    }

    private String getHorizontalBar(int current, int target, int length, String unit) {
        boolean exceeded = current > target;
        double percentage = Math.min((double) current / target * 100, 100);
        int filled = (int) (percentage * length / 100);

        String color = exceeded ? "\u001B[41m" : "\u001B[42m";
        String reset = "\u001B[0m";

        String bar = color + " ".repeat(filled) + reset;
        if (!exceeded) {
            bar += "\u001B[41m" + " ".repeat(length - filled) + reset;
        }

        return String.format("%s %d/%d %s (%+.1f%%) %s",
                bar,
                current,
                target,
                unit,
                (percentage - 100),
                exceeded ? "\u001B[31mEXCEEDED!\u001B[0m" : "");
    }

    private String getVerticalBar(int actual, int target, String label) {
        boolean exceeded = actual > target;
        int maxHeight = 10;
        int targetHeight = (int) ((double) target / actual * maxHeight);

        StringBuilder bar = new StringBuilder(label);
        for (int i = 0; i < maxHeight; i++) {
            String color = (i < targetHeight) ? "\u001B[42m"
                    : (exceeded && i < (actual / (target / maxHeight))) ? "\u001B[41m" : "";
            bar.append(color).append("█").append("\u001B[0m");
        }
        bar.append(String.format(" %d/%d", actual, target));
        if (exceeded)
            bar.append(" \u001B[31mEXCEEDED!\u001B[0m");
        return bar.toString();
    }

    public void exportToCSV(DailySummary summary, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Category,Target,Actual,Difference");
            writeNutritionCSV(writer, summary);
            writeExerciseCSV(writer, summary);
            writeAlarmsCSV(writer, summary);
        }
    }

    private void writeNutritionCSV(PrintWriter writer, DailySummary summary) {
        NutritionSummary nutrition = summary.getNutrition();
        writer.println("Calories," + summary.getTargets().getCalorieTarget() + "," + nutrition.getTotalCalories() + "," + 
                      (nutrition.getTotalCalories() - summary.getTargets().getCalorieTarget()));
        writer.println("Protein," + summary.getTargets().getMacroTargets().getProtein() + "," + 
                      nutrition.getTotalProtein() + "," + (nutrition.getTotalProtein() - summary.getTargets().getMacroTargets().getProtein()));
        writer.println("Carbs," + summary.getTargets().getMacroTargets().getCarbs() + "," + 
                      nutrition.getTotalCarbs() + "," + (nutrition.getTotalCarbs() - summary.getTargets().getMacroTargets().getCarbs()));
        writer.println("Fat," + summary.getTargets().getMacroTargets().getFat() + "," + 
                      nutrition.getTotalFat() + "," + (nutrition.getTotalFat() - summary.getTargets().getMacroTargets().getFat()));
    }

    private void writeExerciseCSV(PrintWriter writer, DailySummary summary) {
        writer.println("Exercise," + formatDuration(summary.getTargets().getExerciseTarget()) +
                "," + formatDuration(summary.getFitness().getTotalExerciseTime()) + "," +
                formatDuration(summary.getFitness().getTotalExerciseTime().minus(summary.getTargets().getExerciseTarget())));
    }

    private void writeAlarmsCSV(PrintWriter writer, DailySummary summary) {
        writer.println("Alarms Triggered," + summary.getAlarms().size());
        summary.getAlarms().forEach(alarm -> writer.println(alarm));
    }
}

