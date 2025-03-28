package src.Diet;

public class NutritionSummary {
    private final int totalCalories;
    private final int totalProtein;
    private final int totalCarbs;
    private final int totalFat;

    public NutritionSummary(int totalCalories, int totalProtein, int totalCarbs, int totalFat) {
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }
    public int getTotalCalories() { return totalCalories; }
    public int getTotalProtein() { return totalProtein; }
    public int getTotalCarbs() { return totalCarbs; }
    public int getTotalFat() { return totalFat; }
}

