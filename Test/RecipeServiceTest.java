package Test;

import org.junit.jupiter.api.Test;

import src.FoodRecipes.Recipe;
import src.FoodRecipes.RecipeService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    @Test
    void testGetRecipesByGoal() {
        // Mock the repository
        RecipeRepository mockRepository = mock(RecipeRepository.class);

        // Create sample data
        Recipe recipe1 = new Recipe("Grilled Chicken", "Muscle Gain",
                Arrays.asList("Chicken Breast", "Olive Oil", "Salt"),
                Arrays.asList("Marinate chicken", "Grill chicken"));
        Recipe recipe2 = new Recipe("Salad", "Weight Loss",
                Arrays.asList("Lettuce", "Tomato", "Cucumber"),
                Arrays.asList("Chop ingredients", "Mix and serve"));

        // Mock the repository behavior
        when(mockRepository.loadRecipes()).thenReturn(List.of(recipe1, recipe2));

        RecipeService service = new RecipeService(mockRepository);

        // Test Muscle Gain recipes
        List<Recipe> muscleGainRecipes = service.getRecipesByGoal("Muscle Gain");
        assertEquals(1, muscleGainRecipes.size());
        assertEquals("Grilled Chicken", muscleGainRecipes.get(0).getName());

        // Test Weight Loss recipes
        List<Recipe> weightLossRecipes = service.getRecipesByGoal("Weight Loss");
        assertEquals(1, weightLossRecipes.size());
        assertEquals("Salad", weightLossRecipes.get(0).getName());
    }

    @Test
    void testGetRecipesByIngredient() {
        // Mock the repository
        RecipeRepository mockRepository = mock(RecipeRepository.class);

        // Create sample data
        Recipe recipe1 = new Recipe("Grilled Chicken", "Muscle Gain",
                Arrays.asList("Chicken Breast", "Olive Oil", "Salt"),
                Arrays.asList("Marinate chicken", "Grill chicken"));
        Recipe recipe2 = new Recipe("Salad", "Weight Loss",
                Arrays.asList("Lettuce", "Tomato", "Cucumber"),
                Arrays.asList("Chop ingredients", "Mix and serve"));

        // Mock the repository behavior
        when(mockRepository.loadRecipes()).thenReturn(List.of(recipe1, recipe2));

        RecipeService service = new RecipeService(mockRepository);

        // Test ingredient search
        List<Recipe> chickenRecipes = service.getRecipesByIngredient("Chicken");
        assertEquals(1, chickenRecipes.size());
        assertEquals("Grilled Chicken", chickenRecipes.get(0).getName());

        List<Recipe> oilRecipes = service.getRecipesByIngredient("Oil");
        assertEquals(1, oilRecipes.size());
        assertEquals("Grilled Chicken", oilRecipes.get(0).getName());
    }
}

