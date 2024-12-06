import org.junit.jupiter.api.Test;

import src.FoodRecipes.Recipe;
import src.FoodRecipes.RecipeRepository;
import src.FoodRecipes.RecipeService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    @Test
    void testGetRecipesByGoal() throws IOException {
        // Setup: Create a temporary directory and recipe files
        Path tempDirectory = Files.createTempDirectory("test-recipes");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempDirectory.resolve("GrilledChicken.txt"))) {
                writer.write("Grilled Chicken\n");
                writer.write("Muscle Gain\n");
                writer.write("Chicken Breast\nOlive Oil\nSalt\n---\n");
                writer.write("Marinate chicken\nGrill chicken\n");
            }

            try (BufferedWriter writer = Files.newBufferedWriter(tempDirectory.resolve("Salad.txt"))) {
                writer.write("Salad\n");
                writer.write("Weight Loss\n");
                writer.write("Lettuce\nTomato\nCucumber\n---\n");
                writer.write("Chop vegetables\nMix ingredients\n");
            }

            RecipeRepository repository = new RecipeRepository(tempDirectory.toString());
            RecipeService service = new RecipeService(repository);

            // Action: Get recipes by goal
            List<Recipe> muscleGainRecipes = service.getRecipesByGoal("Muscle Gain");
            List<Recipe> weightLossRecipes = service.getRecipesByGoal("Weight Loss");

            // Assertions
            assertEquals(1, muscleGainRecipes.size());
            assertEquals("Grilled Chicken", muscleGainRecipes.get(0).getName());

            assertEquals(1, weightLossRecipes.size());
            assertEquals("Salad", weightLossRecipes.get(0).getName());
        } finally {
            // Cleanup
            Files.walk(tempDirectory).map(Path::toFile).forEach(File::delete);
            tempDirectory.toFile().delete();
        }
    }

    @Test
    void testGetRecipesByIngredient() throws IOException {
        // Setup: Create a temporary directory and recipe files
        Path tempDirectory = Files.createTempDirectory("test-recipes");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempDirectory.resolve("GrilledChicken.txt"))) {
                writer.write("Grilled Chicken\n");
                writer.write("Muscle Gain\n");
                writer.write("Chicken Breast\nOlive Oil\nSalt\n---\n");
                writer.write("Marinate chicken\nGrill chicken\n");
            }

            try (BufferedWriter writer = Files.newBufferedWriter(tempDirectory.resolve("Salad.txt"))) {
                writer.write("Salad\n");
                writer.write("Weight Loss\n");
                writer.write("Lettuce\nTomato\nCucumber\n---\n");
                writer.write("Chop vegetables\nMix ingredients\n");
            }

            RecipeRepository repository = new RecipeRepository(tempDirectory.toString());
            RecipeService service = new RecipeService(repository);

            // Action: Search recipes by ingredient
            List<Recipe> chickenRecipes = service.getRecipesByIngredient("Chicken");
            List<Recipe> oilRecipes = service.getRecipesByIngredient("Oil");

            // Assertions
            assertEquals(1, chickenRecipes.size());
            assertEquals("Grilled Chicken", chickenRecipes.get(0).getName());

            assertEquals(1, oilRecipes.size());
            assertEquals("Grilled Chicken", oilRecipes.get(0).getName());
        } finally {
            // Cleanup
            Files.walk(tempDirectory).map(Path::toFile).forEach(File::delete);
            tempDirectory.toFile().delete();
        }
    }
}
