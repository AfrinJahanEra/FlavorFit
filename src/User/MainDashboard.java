package src.User;

import java.util.ArrayList;
import java.util.List;
import src.DietPlanner.NutritionTracker;
import src.Exercise.ExerciseTracker;
import src.FileManager.RecipeRepository;
import src.FileManager.SimpleFileLoader;
import src.FoodRecipes.RecipeTracker;
import src.HealthFactor.HealthFactor;
import src.VirtualNutritionist.VirtualNutritionist;

public class MainDashboard extends BaseFeature {
    private final List<BaseFeature> features;
    
    public MainDashboard(User user) {
        this.features = initializeFeatures(user);
    }
    
    private List<BaseFeature> initializeFeatures(User user) {
        List<BaseFeature> features = new ArrayList<>();
        SimpleFileLoader fileLoader = new SimpleFileLoader();
        
        try {
            features.add(new HealthFactor(user));
            features.add(new RecipeTracker(new RecipeRepository("data/recipes")));
            features.add(new ExerciseTracker(user));
            features.add(new VirtualNutritionist(user));
            features.add(new NutritionTracker());
        } catch (Exception e) {
            System.err.println("Warning: Some features may not work - " + e.getMessage());
        }
        
        return features;
    }
    
    @Override
    public String getTitle() { return "Main Dashboard"; }
    
    @Override
    public void display() {
        String[] options = features.stream()
            .map(BaseFeature::getTitle)
            .toArray(String[]::new);
        
        Runnable[] handlers = features.stream()
            .map(feature -> (Runnable) feature::display)
            .toArray(Runnable[]::new);
        
        displayMenuUntilExit(getTitle(), options, handlers);
    }
}