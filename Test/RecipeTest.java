package Test;

import org.junit.jupiter.api.Test;

import src.FoodRecipes.Recipe;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testRecipeInitialization() {
        List<String> ingredients = Arrays.asList("Chicken Breast", "Olive Oil", "Salt");
        List<String> steps = Arrays.asList("Marinate chicken", "Grill chicken");

        Recipe recipe = new Recipe("Grilled Chicken", "Muscle Gain", ingredients, steps);

        assertEquals("Grilled Chicken", recipe.getName());
        assertEquals("Muscle Gain", recipe.getGoal());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals(steps, recipe.getSteps());
    }

    @Test
    void testRecipeDisplay() {
        List<String> ingredients = Arrays.asList("Lettuce", "Tomato", "Cucumber");
        List<String> steps = Arrays.asList("Chop ingredients", "Mix and serve");

        Recipe recipe = new Recipe("Salad", "Weight Loss", ingredients, steps);

        assertDoesNotThrow(recipe::display); // Ensure display method doesn't throw any exception
    }
}

