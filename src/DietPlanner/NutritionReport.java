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
        System.out.println("\n[Graphical Representation]");
        printBarChart("Calories", progress.getCalories(), target.getCalorieTarget());
        printBarChart("Protein", progress.getProtein(), target.getProteinTarget());
        printBarChart("Carbs", progress.getCarbs(), target.getCarbsTarget());
        printBarChart("Fat", progress.getFat(), target.getFatTarget());
        printBarChart("Exercise", progress.getExerciseTime(), target.getExerciseTarget());
    }

    private void printBarChart(String label, double progress, double target) {
        int maxBars = 50;
        int bars = (int) ((progress / target) * maxBars);
        bars = Math.min(bars, maxBars);
        System.out.print(label + ": [");
        System.out.print("=".repeat(bars));
        System.out.print(" ".repeat(maxBars - bars));
        System.out.printf("] %.1f/%.1f\n", progress, target);
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