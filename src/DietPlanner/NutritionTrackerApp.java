package src.DietPlanner;
public class NutritionTrackerApp {
    public static void start() {
        NutritionDataManager dataManager = new NutritionDataManager();
        UserProfile userProfile = new UserProfile(dataManager);
        MenuController menuController = new MenuController(userProfile, dataManager);

        userProfile.loadData(); // Load user profile data
        menuController.displayMainMenu(); // Display the main menu
    }
}









// Report Generator


