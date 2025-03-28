package src.Diet;

import java.time.LocalDateTime;

public class Meal implements Trackable {
    private final String name;
    private final NutritionalInfo nutritionalInfo;
    private final LocalDateTime consumedAt;

    public Meal(String name, NutritionalInfo nutritionalInfo) {
        this.name = name;
        this.nutritionalInfo = nutritionalInfo;
        this.consumedAt = LocalDateTime.now();
    }

    @Override
    public String getName() { return name; }
    @Override
    public int getCalories() { return nutritionalInfo.getCalories(); }
    
    public NutritionalInfo getNutritionalInfo() { return nutritionalInfo; }
    public LocalDateTime getConsumedAt() { return consumedAt; }
}
