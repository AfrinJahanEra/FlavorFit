package src.DietPlanner;

class TargetChecker {
    private final UserTarget target;
    private final UserProgress progress;

    public TargetChecker(UserTarget target, UserProgress progress) {
        this.target = target;
        this.progress = progress;
    }

    public void checkExceedingTargets() {
        boolean exceeded = false;

        exceeded |= checkExceeded("calorie", progress.getCalories(), target.getCalorieTarget());
        exceeded |= checkExceeded("protein", progress.getProtein(), target.getProteinTarget());
        exceeded |= checkExceeded("carbs", progress.getCarbs(), target.getCarbsTarget());
        exceeded |= checkExceeded("fat", progress.getFat(), target.getFatTarget());

        if (!exceeded) {
            System.out.println("You're within your targets. Good job!");
        }
    }

    private boolean checkExceeded(String nutrient, double current, double target) {
        if (current > target) {
            System.out.printf("Warning: You have exceeded your %s target!%n", nutrient);
            return true;
        }
        return false;
    }
}