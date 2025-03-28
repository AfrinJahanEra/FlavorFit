package src.Diet;

public class NutritionalInfo {
    private final int calories;
    private final double protein;
    private final double carbs;
    private final double fat;

    public NutritionalInfo(int calories, double protein, double carbs, double fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    // Getters
    public int getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFat() { return fat; }
}