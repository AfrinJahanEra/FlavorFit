package src.DietPlanner;

class NutritionReport {
    private final UserTarget target;
    private final UserProgress progress;

    // ANSI Color Codes
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";

    public NutritionReport(UserTarget target, UserProgress progress) {
        this.target = target;
        this.progress = progress;
    }

    public void display() {
        printHeader("Nutrition & Fitness Report");
        displayProgressVsTargets();
        displayGraphicalRepresentation();
        displayStatus();
    }

    private void printHeader(String title) {
        System.out.println("    " + CENTER_TEXT(title, 38) + "   ");
        System.out.println("════════════════════════════════════════════" + RESET);
    }

    private String CENTER_TEXT(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
    }

    private void displayProgressVsTargets() {
        System.out.println(YELLOW + BOLD + "\n[ Current Progress vs Targets ]" + RESET);
        System.out.printf("%-10s %12s %5s %12s\n", "Nutrient", "Progress", "", "Target");
        System.out.println("───────────────────────────────────────────");
        
        printNutrientRow("Calories", progress.getCalories(), target.getCalorieTarget(), "kcal");
        printNutrientRow("Protein", progress.getProtein(), target.getProteinTarget(), "g");
        printNutrientRow("Carbs", progress.getCarbs(), target.getCarbsTarget(), "g");
        printNutrientRow("Fat", progress.getFat(), target.getFatTarget(), "g");
        printNutrientRow("Exercise", progress.getExerciseTime(), target.getExerciseTarget(), "min");
    }

    private void printNutrientRow(String name, double progressVal, double targetVal, String unit) {
        String progressColor = progressVal > targetVal ? RED : GREEN;
        System.out.printf(CYAN + "%-10s" + RESET + " " + progressColor + "%10.1f" + RESET + " %3s " + 
                         PURPLE + "%10.1f" + RESET + " %-3s\n",
                name, progressVal, unit, targetVal, unit);
    }

    private void displayGraphicalRepresentation() {
        System.out.println(YELLOW + BOLD + "\n[ Advanced Nutrition Progress Chart ]" + RESET);
        
        String[] nutrients = {"Calories", "Protein", "Carbs", "Fat", "Exercise"};
        String[] units = {"kcal", "g", "g", "g", "min"};
        double[] progressValues = {
            progress.getCalories(),
            progress.getProtein(),
            progress.getCarbs(),
            progress.getFat(),
            progress.getExerciseTime()
        };
        double[] targetValues = {
            target.getCalorieTarget(),
            target.getProteinTarget(),
            target.getCarbsTarget(),
            target.getFatTarget(),
            target.getExerciseTarget()
        };
        
        int chartWidth = 50;
        double maxValue = getMaxValue(progressValues, targetValues);
        double scale = maxValue > 0 ? chartWidth / maxValue : 1;
        
        // Print chart rows
        for (int i = 0; i < nutrients.length; i++) {
            printChartRow(nutrients[i], units[i], progressValues[i], targetValues[i], scale, chartWidth);
        }
        
        printXAxis(maxValue, chartWidth, scale);
        
        System.out.println("\n" + GREEN + "Legend: " + RESET + 
                         "| = Target, " + GREEN + "o" + RESET + " = On Track, " + 
                         RED + "X" + RESET + " = Exceeded");
    }

    private double getMaxValue(double[] progress, double[] targets) {
        double max = 0;
        for (int i = 0; i < progress.length; i++) {
            max = Math.max(max, Math.max(progress[i], targets[i]));
        }
        return max;
    }

    private void printChartRow(String nutrient, String unit, double progressVal, double targetVal, double scale, int width) {
        int targetPos = (int) (targetVal * scale);
        int progressPos = (int) (progressVal * scale);
        
        System.out.printf(CYAN + "%-8s" + RESET + " %4.1f/%4.1f %-4s │", 
                         nutrient, progressVal, targetVal, unit);
        
        for (int j = 0; j <= width; j++) {
            if (j == targetPos) {
                System.out.print(BLUE + "|" + RESET);
            } else if (j == progressPos) {
                System.out.print(progressVal > targetVal ? RED + "X" + RESET : GREEN + "o" + RESET);
            } else if (j < progressPos) {
                System.out.print(progressVal > targetVal && j > targetPos ? RED + "─" + RESET : "─");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private void printXAxis(double maxValue, int chartWidth, double scale) {
        // Print the axis line
        System.out.print("                         ");  // Match the indentation of the chart rows
        for (int i = 0; i <= chartWidth; i++) {
            if (i % 10 == 0) {
                System.out.print(BLUE + "┼" + RESET);
            } else if (i % 5 == 0) {
                System.out.print(BLUE + "┬" + RESET);
            } else {
                System.out.print(BLUE + "─" + RESET);
            }
        }
        System.out.println();
        
        // Print the scale numbers
        System.out.print("          ");  // Extra space to align with first character after label
        for (int i = 0; i <= chartWidth; i += 10) {
            int value = (int)(i / scale);
            // Adjust spacing based on number of digits
            if (i == 0) {
                System.out.printf("                         ", value);
            } else {
                System.out.printf("%-10d", value);
            }
        }
        System.out.println();
    }

    private void displayStatus() {
        System.out.println(YELLOW + BOLD + "\n[ Status Summary ]" + RESET);
        new TargetChecker(target, progress).checkExceedingTargets();
        
        if (progress.getExerciseTime() >= target.getExerciseTarget()) {
            System.out.println(GREEN + "✓ You've met your exercise goal! Great job!" + RESET);
        } else {
            System.out.printf(YELLOW + "! Exercise remaining: " + RESET + 
                            "%d minutes\n", 
                            (target.getExerciseTarget() - progress.getExerciseTime()));
        }
    }
}