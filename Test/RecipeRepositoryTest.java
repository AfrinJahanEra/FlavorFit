import org.junit.jupiter.api.Test;

import src.FoodRecipes.Recipe;
import src.FoodRecipes.RecipeRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRepositoryTest {

    @Test
    void testLoadRecipes() throws IOException {
        // Setup: Create a temporary directory and recipe file
        Path tempDirectory = Files.createTempDirectory("test-recipes");
        try {
            Path recipeFile = tempDirectory.resolve("GrilledChicken.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(recipeFile)) {
                writer.write("Grilled Chicken\n");
                writer.write("Muscle Gain\n");
                writer.write("Chicken Breast\nOlive Oil\nSalt\n---\n");
                writer.write("Marinate chicken\nGrill chicken\n");
            }

            RecipeRepository repository = new RecipeRepository(tempDirectory.toString());
            List<Recipe> recipes = repository.loadRecipes();

            // Assertions
            assertEquals(1, recipes.size());
            Recipe recipe = recipes.get(0);
            assertEquals("Grilled Chicken", recipe.getName());
            assertEquals("Muscle Gain", recipe.getGoal());
            assertTrue(recipe.getIngredients().contains("Chicken Breast"));
        } finally {
            // Cleanup: Delete the temporary directory and its files
            Files.walk(tempDirectory).map(Path::toFile).forEach(File::delete);
            tempDirectory.toFile().delete();
        }
    }

    @Test
    void testSaveRecipe() throws IOException {
        // Setup: Create a temporary directory
        Path tempDirectory = Files.createTempDirectory("test-recipes");
        try {
            RecipeRepository repository = new RecipeRepository(tempDirectory.toString());

            Recipe newRecipe = new Recipe(
                    "Salad",
                    "Weight Loss",
                    List.of("Lettuce", "Tomato", "Cucumber"),
                    List.of("Chop vegetables", "Mix ingredients")
            );

            // Action: Save the recipe
            repository.saveRecipe(newRecipe);

            // Assertions
            Path savedFile = tempDirectory.resolve("Salad.txt");
            assertTrue(Files.exists(savedFile));

            List<String> lines = Files.readAllLines(savedFile);
            assertEquals("Salad", lines.get(0));
            assertEquals("Weight Loss", lines.get(1));
            assertTrue(lines.contains("Lettuce"));
        } finally {
            // Cleanup: Delete the temporary directory and its files
            Files.walk(tempDirectory).map(Path::toFile).forEach(File::delete);
            tempDirectory.toFile().delete();
        }
    }
}
