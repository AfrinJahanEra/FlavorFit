package src.Diet;


import java.util.Optional;

public interface FoodDatabase {
    Optional<NutritionalInfo> lookupFood(String foodName);
    void addFood(String foodName, NutritionalInfo info);
    static Object get(String lowerCase) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}