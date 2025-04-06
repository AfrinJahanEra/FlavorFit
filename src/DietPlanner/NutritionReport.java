package src.DietPlanner;

class NutritionReport {
    private final UserTarget target;
    private final UserProgress progress;

    public NutritionReport(UserTarget target, UserProgress progress) {
        this.target = target;
        this.progress = progress;
    }

    public void display() {
        System.out.println("\n--- Nutrition & Fitness Report ---");
        displayProgressVsTargets();
        displayGraphicalRepresentation();
        displayStatus();
    }

    private void displayProgressVsTargets() {
        System.out.println("\n[Current Progress vs Targets]");
        System.out.println("Calories: " + progress.getCalories() + "/" + target.getCalorieTarget());
        System.out.println("Protein: " + progress.getProtein() + "g/" + target.getProteinTarget() + "g");
        System.out.println("Carbs: " + progress.getCarbs() + "g/" + target.getCarbsTarget() + "g");
        System.out.println("Fat: " + progress.getFat() + "g/" + target.getFatTarget() + "g");
        System.out.println("Exercise: " + progress.getExerciseTime() + "min/" + target.getExerciseTarget() + "min");
    }

    private void displayGraphicalRepresentation() {
        System.out.println("\n[Advanced Nutrition Progress Chart]");
        
        // Define the nutrition items and their values
        String[] nutrients = {"Calories", "Protein", "Carbs", "Fat", "Exercise"};
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
        
        // Calculate scaling factor for the chart
        double maxValue = 0;
        for (int i = 0; i < nutrients.length; i++) {
            maxValue = Math.max(maxValue, Math.max(progressValues[i], targetValues[i]));
        }
        int chartWidth = 50;
        double scale = chartWidth / maxValue;
        
        // Print Y-axis labels and bars
        for (int i = 0; i < nutrients.length; i++) {
            // Format the nutrient name (Y-axis label)
            System.out.printf("%-8s | ", nutrients[i]);
            
            int targetPos = (int) (targetValues[i] * scale);
            int progressPos = (int) (progressValues[i] * scale);
            
            // Print the bar
            for (int j = 0; j <= chartWidth; j++) {
                if (j == targetPos) {
                    // Target position marker
                    System.out.print("|");
                } else if (j == progressPos) {
                    // Progress position - show with different character if exceeded
                    if (progressValues[i] > targetValues[i]) {
                        System.out.print("\u001B[31mX\u001B[0m"); // Red X for exceeded
                    } else {
                        System.out.print("o"); // Regular marker
                    }
                } else if (j < progressPos && j < targetPos) {
                    // Before both progress and target
                    System.out.print("-");
                } else if (j < progressPos) {
                    // Exceeded target area
                    System.out.print("\u001B[31m-\u001B[0m"); // Red for exceeded
                } else {
                    // After both
                    System.out.print(" ");
                }
            }
            
            // Print the numeric values
            System.out.printf(" %.1f/%s", progressValues[i], 
                targetValues[i] == (int)targetValues[i] ? 
                String.valueOf((int)targetValues[i]) : 
                String.valueOf(targetValues[i]));
            
            System.out.println();
        }
        
        // Print X-axis
        System.out.print("         +");
        for (int i = 0; i <= chartWidth; i += 5) {
            System.out.printf("+----%-4d", (int)(i / scale));
        }
        System.out.println();
        
        // Legend
        System.out.println("Legend: | = Target, o = Progress, X = Exceeded (shown in red)");
    }
    

    private void displayStatus() {
        System.out.println("\n[Status]");
        new TargetChecker(target, progress).checkExceedingTargets();
        if (progress.getExerciseTime() >= target.getExerciseTarget()) {
            System.out.println("You've met your exercise goal! Great job!");
        } else {
            System.out.println("Exercise remaining: " +
                    (target.getExerciseTarget() - progress.getExerciseTime()) + " minutes");
        }
    }
}