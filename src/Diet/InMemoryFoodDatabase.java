package src.Diet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
public class InMemoryFoodDatabase implements FoodDatabase {
    private final Map<String, NutritionalInfo> foodDatabase = new HashMap<>();

    @Override
    public Optional<NutritionalInfo> lookupFood(String foodName) {
        return Optional.ofNullable(foodDatabase.get(foodName.toLowerCase()));
    }

    @Override
    public void addFood(String foodName, NutritionalInfo info) {
        foodDatabase.put(foodName.toLowerCase(), info);
    }
}