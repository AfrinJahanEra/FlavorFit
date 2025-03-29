package src.DietPlanner;

class UserTarget {
    private int calorieTarget;
    private double proteinTarget;
    private double carbsTarget;
    private double fatTarget;
    private int exerciseTarget;

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    public void setProteinTarget(double proteinTarget) {
        this.proteinTarget = proteinTarget;
    }

    public void setCarbsTarget(double carbsTarget) {
        this.carbsTarget = carbsTarget;
    }

    public void setFatTarget(double fatTarget) {
        this.fatTarget = fatTarget;
    }

    public void setExerciseTarget(int exerciseTarget) {
        this.exerciseTarget = exerciseTarget;
    }

    public int getCalorieTarget() {
        return calorieTarget;
    }

    public double getProteinTarget() {
        return proteinTarget;
    }

    public double getCarbsTarget() {
        return carbsTarget;
    }

    public double getFatTarget() {
        return fatTarget;
    }

    public int getExerciseTarget() {
        return exerciseTarget;
    }
}
