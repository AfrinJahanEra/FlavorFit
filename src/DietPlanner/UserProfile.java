package src.DietPlanner;

class UserProfile {
    private final NutritionDataManager dataManager;
    private UserTarget userTarget;
    private UserProgress userProgress;

    public UserProfile(NutritionDataManager dataManager) {
        this.dataManager = dataManager;
        this.userTarget = new UserTarget();
        this.userProgress = new UserProgress();
    }

    public void loadData() {
        dataManager.loadFoodDatabase();
        this.userTarget = dataManager.loadTargets();
    }

    public void saveTargets() {
        dataManager.saveTargets(userTarget);
    }

    public UserTarget getUserTarget() {
        return userTarget;
    }

    public UserProgress getUserProgress() {
        return userProgress;
    }

    public void addFoodConsumption(Food food) {
        userProgress.addFood(food);
    }

    public void addExerciseTime(int minutes) {
        userProgress.addExerciseTime(minutes);
    }

    public void updateTargets(int calories, double protein, double carbs, double fat, int exercise) {
        userTarget.setCalorieTarget(calories);
        userTarget.setProteinTarget(protein);
        userTarget.setCarbsTarget(carbs);
        userTarget.setFatTarget(fat);
        userTarget.setExerciseTarget(exercise);
        saveTargets();
        SoundPlayer.playSound();
    }

    public NutritionReport generateReport() {
        return new NutritionReport(userTarget, userProgress);
    }
}