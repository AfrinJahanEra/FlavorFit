package src.Diet;

import java.io.IOException;
import src.Diet.PersistentFoodDatabase.FoodDatabaseException;

public interface FoodPersistence {
    void saveToFile() throws FoodDatabaseException;
    void loadFromFile() throws FoodDatabaseException, IOException;
}
