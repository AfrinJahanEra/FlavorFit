package src.DietPlanner;

import java.util.Optional;

interface FoodRepository {
    Optional<Food> findFood(String foodName);

    void loadFoodDatabase();
}