package src.User;

import java.io.IOException;
import java.util.List;
import src.DietPlanner.NutritionTrackerApp;
import src.Exercise.ExerciseMain;
import src.FoodRecipes.RecipeApp;
import src.FoodRecipes.RecipeRepository;
import src.HealthFactor.HealthFactorMain;
import src.VirtualNutritionist.VirtualNutritionist;

public class MainDashboard extends BaseFeature {
    private final List<Feature> features;
    
    public MainDashboard(User user) throws IOException {
        this.features = List.of(
            new HealthFactorMain(user),
            new RecipeApp(new RecipeRepository("src/recipes")),
            new ExerciseMain(user),
            new VirtualNutritionist(user),
            new NutritionTrackerApp()
        );
    }
    
    @Override
    public String getTitle() { return "Main Dashboard"; }
    
    @Override
    public void display() {
        while (true) {
            String[] options = features.stream()
                .map(Feature::getTitle)
                .toArray(String[]::new);
            
            Runnable[] handlers = features.stream()
                .map(feature -> (Runnable) feature::display)
                .toArray(Runnable[]::new);
            
            displayMenu(getTitle(), options, handlers);
            
            // If user selected "Back", exit the loop
            if (getIntInput("Press 1 to continue or 2 to exit: ", 1, 2) == 2) {
                return;
            }
        }
    }
}