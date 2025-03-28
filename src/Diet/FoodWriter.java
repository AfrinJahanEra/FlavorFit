package src.Diet;

import src.Diet.PersistentFoodDatabase.FoodDatabaseException;

public interface FoodWriter extends FoodLookup {
    void addFood(String foodName, NutritionalInfo info) throws FoodDatabaseException;
}