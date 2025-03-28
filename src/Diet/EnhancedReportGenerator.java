package src.Diet;


import java.time.Duration;

public class EnhancedReportGenerator extends ReportGenerator {
    @Override
    public void printGraphicalSummary(DailySummary summary) {
        System.out.println("\n=== NUTRITION PROGRESS ===");
        printNutritionProgressBars(summary);
        
        System.out.println("\n=== EXERCISE PROGRESS ===");
        printExerciseProgress(summary);
        
        System.out.println("\n=== MACRONUTRIENT BALANCE ===");
        printMacroNutrientBalance(summary);
    }

    private void printNutritionProgressBars(DailySummary summary) {
        NutritionSummary nutrition = summary.getNutrition();
        DailyTargets targets = summary.getTargets();
        
        printProgressBar("Calories", nutrition.getTotalCalories(), targets.getCalorieTarget(), "kcal");
        printProgressBar("Protein", nutrition.getTotalProtein(), targets.getMacroTargets().getProtein(), "g");
        printProgressBar("Carbs", nutrition.getTotalCarbs(), targets.getMacroTargets().getCarbs(), "g");
        printProgressBar("Fat", nutrition.getTotalFat(), targets.getMacroTargets().getFat(), "g");
    }

    private void printProgressBar(String label, double current, double target, String unit) {
        double percentage = Math.min(current / target * 100, 100);
        int filledWidth = (int) (percentage / 5);
        
        String color = percentage > 100 ? "\u001B[31m" : "\u001B[32m";
        String reset = "\u001B[0m";
        
        System.out.printf("%-8s: %5.1f/%5.1f %-4s ", label, current, target, unit);
        System.out.print(color + "[");
        for (int i = 0; i < 20; i++) {
            System.out.print(i < filledWidth ? "█" : " ");
        }
        System.out.printf("] %3.0f%%%s%n", percentage, reset);
    }

    private void printExerciseProgress(DailySummary summary) {
        Duration current = summary.getFitness().getTotalExerciseTime();
        Duration target = summary.getTargets().getExerciseTarget();
        
        double currentMinutes = current.toMinutes();
        double targetMinutes = target.toMinutes();
        double percentage = Math.min(currentMinutes / targetMinutes * 100, 100);
        
        System.out.printf("Exercise Time: %.1f/%.1f minutes (%.0f%%)%n",
                currentMinutes, targetMinutes, percentage);
        
        String progress = getExerciseVisualProgress(current, target);
        System.out.println(progress);
    }

    private String getExerciseVisualProgress(Duration current, Duration target) {
        long currentSec = current.getSeconds();
        long targetSec = target.getSeconds();
        int progressWidth = 20;
        int filled = (int) ((double) currentSec / targetSec * progressWidth);
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < progressWidth; i++) {
            sb.append(i < filled ? "=" : " ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void printMacroNutrientBalance(DailySummary summary) {
        NutritionSummary nutrition = summary.getNutrition();
        double total = nutrition.getTotalProtein() + nutrition.getTotalCarbs() + nutrition.getTotalFat();
        
        if (total <= 0) {
            System.out.println("No macronutrient data available");
            return;
        }
        
        double proteinPercent = nutrition.getTotalProtein() / total * 100;
        double carbsPercent = nutrition.getTotalCarbs() / total * 100;
        double fatPercent = nutrition.getTotalFat() / total * 100;
        
        System.out.printf("Protein: %5.1f%% \u001B[34m%s\u001B[0m%n",
                proteinPercent, getMacroBar(proteinPercent));
        System.out.printf("Carbs:   %5.1f%% \u001B[32m%s\u001B[0m%n",
                carbsPercent, getMacroBar(carbsPercent));
        System.out.printf("Fat:     %5.1f%% \u001B[33m%s\u001B[0m%n",
                fatPercent, getMacroBar(fatPercent));
    }

    private String getMacroBar(double percentage) {
        int width = (int) (percentage / 5);
        return "▇".repeat(width) + " " + (100 - width * 5) + "%";
    }
}