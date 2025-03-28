package src.Diet;

public class NutritionalInfo {
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fat;

    public NutritionalInfo(int calories, int protein, int carbs, int fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    // Getters
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }
}