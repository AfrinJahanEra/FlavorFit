package src.Diet;

import java.util.Optional;

public interface FoodLookup {
    Optional<NutritionalInfo> lookupFood(String foodName);
}
