package src.DietPlanner;

class UserProgress {
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private int exerciseTime;

    public void addFood(Food food) {
        calories += food.getCalories();
        protein += food.getProtein();
        carbs += food.getCarbs();
        fat += food.getFat();
    }

    public void addExerciseTime(int minutes) {
        exerciseTime += minutes;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public int getExerciseTime() {
        return exerciseTime;
    }

    @Override
    public String toString() {
        return String.format("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg, Exercise: %dmin",
                calories, protein, carbs, fat, exerciseTime);
    }
}