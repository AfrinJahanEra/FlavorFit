package src.User;

import java.util.ArrayList;
import java.util.List;
import src.DietPlanner.NutritionTracker;
import src.Exercise.ExerciseTracker;
import src.FileManager.RecipeRepository;
import src.FoodRecipes.RecipeTracker;
import src.HealthFactor.HealthFactor;
import src.VirtualNutritionist.VirtualNutritionist;


public class MainDashboard { 
    private final List<BaseFeature> features;
    private final BaseFeature baseFeatureDelegate;  
    
    public MainDashboard(User user) {
        this.baseFeatureDelegate = new BaseFeature() {
            @Override
            public String getTitle() { return "Main Dashboard"; }
            
            @Override
            public void display() {
                // Empty implementation since we override display() below
            }
        };
        this.features = initializeFeatures(user);
    }
    
    private List<BaseFeature> initializeFeatures(User user) {
        // Keep the exact same implementation as before
        List<BaseFeature> features = new ArrayList<>();
        
        try {
            features.add(new HealthFactor(user));
            features.add(new VirtualNutritionist(user));
            features.add(new RecipeTracker(new RecipeRepository("src\\recipes")));
            features.add(new ExerciseTracker(user));
            features.add(new NutritionTracker());
            
        } catch (Exception e) {
            System.err.println("Warning: Some features may not work - " + e.getMessage());
        }
        
        return features;
    }
    
    // Delegate getTitle() to our composed BaseFeature
    public String getTitle() { 
        return baseFeatureDelegate.getTitle(); 
    }
    
    // Modified display() method that uses composition
    public void display() {
        String[] options = features.stream()
            .map(BaseFeature::getTitle)
            .toArray(String[]::new);
        
        Runnable[] handlers = features.stream()
            .map(feature -> (Runnable) feature::display)
            .toArray(Runnable[]::new);
        
        // Use the displayMenuUntilExit from our composed BaseFeature
        baseFeatureDelegate.displayMenuUntilExit(getTitle(), options, handlers);
    }
}