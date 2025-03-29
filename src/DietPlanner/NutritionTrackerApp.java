package src.DietPlanner;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

// Main Application Class
public class NutritionTrackerApp {
    public static void start() {
        NutritionDataManager dataManager = new NutritionDataManager();
        UserProfile userProfile = new UserProfile(dataManager);
        MenuController menuController = new MenuController(userProfile, dataManager);

        userProfile.loadData(); // Load user profile data
        menuController.displayMainMenu(); // Display the main menu
    }
}

// Data Interfaces
interface FoodRepository {
    Optional<Food> findFood(String foodName);

    void loadFoodDatabase();
}

interface TargetStorage {
    UserTarget loadTargets();

    void saveTargets(UserTarget userTarget);
}

// Data Manager implements both interfaces
class NutritionDataManager implements FoodRepository, TargetStorage {
    private static final String NUTRITION_FILE = "nutrition.txt";
    private static final String TARGETS_FILE = "targets.txt";
    private final Map<String, Food> foodDatabase = new HashMap<>();

    @Override
    public void loadFoodDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NUTRITION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    Food food = new Food(parts[0], Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]));
                    foodDatabase.put(parts[0].toLowerCase(), food);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading food database: " + e.getMessage());
        }
    }

    @Override
    public Optional<Food> findFood(String foodName) {
        return Optional.ofNullable(foodDatabase.get(foodName.toLowerCase()));
    }

    @Override
    public UserTarget loadTargets() {
        UserTarget target = new UserTarget();
        try (BufferedReader reader = new BufferedReader(new FileReader(TARGETS_FILE))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    target.setCalorieTarget(Integer.parseInt(parts[0]));
                    target.setProteinTarget(Double.parseDouble(parts[1]));
                    target.setCarbsTarget(Double.parseDouble(parts[2]));
                    target.setFatTarget(Double.parseDouble(parts[3]));
                    target.setExerciseTarget(Integer.parseInt(parts[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing targets found. Using default values.");
        }
        return target;
    }

    @Override
    public void saveTargets(UserTarget userTarget) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TARGETS_FILE))) {
            writer.printf("%d,%.1f,%.1f,%.1f,%d",
                    userTarget.getCalorieTarget(),
                    userTarget.getProteinTarget(),
                    userTarget.getCarbsTarget(),
                    userTarget.getFatTarget(),
                    userTarget.getExerciseTarget());
        } catch (IOException e) {
            System.out.println("Error saving targets: " + e.getMessage());
        }
    }
}

// User Profile
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

class SoundPlayer {

    private static final String SOUND_FILE_PATH = "mixkit-software-interface-start-2574.wav";

    public static void playSound() {
        try {
            File soundFile = new File(SOUND_FILE_PATH);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + SOUND_FILE_PATH);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Don't sleep here - let the sound play in the background
            // Thread.sleep(clip.getMicrosecondLength() / 1000);

            // Instead, add a listener to close the clip when done
            clip.addLineListener(event -> {
                if (event.getType().equals(javax.sound.sampled.LineEvent.Type.STOP)) {
                    clip.close();
                    try {
                        audioInputStream.close();
                    } catch (Exception e) {
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Menu Controller
class MenuController {
    private final UserProfile userProfile;
    private final FoodRepository foodRepository;
    private final Scanner scanner;

    public MenuController(UserProfile userProfile, FoodRepository foodRepository) {
        this.userProfile = userProfile;
        this.foodRepository = foodRepository;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n--- Nutrition Tracker ---");
            System.out.println("1. Set Targets");
            System.out.println("2. Add Food");
            System.out.println("3. Start Exercise Timer");
            System.out.println("4. Show Report");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> setTargets();
                case 2 -> addFood();
                case 3 -> startExerciseTimer();
                case 4 -> showReport();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void setTargets() {
        System.out.print("Enter calorie target: ");
        int calories = scanner.nextInt();
        System.out.print("Enter protein target (g): ");
        double protein = scanner.nextDouble();
        System.out.print("Enter carbs target (g): ");
        double carbs = scanner.nextDouble();
        System.out.print("Enter fat target (g): ");
        double fat = scanner.nextDouble();
        System.out.print("Enter exercise target (minutes): ");
        int exercise = scanner.nextInt();
        scanner.nextLine();

        userProfile.updateTargets(calories, protein, carbs, fat, exercise);
        System.out.println("Targets set successfully!");
    }

    private void addFood() {
        System.out.print("Enter food name: ");
        String foodName = scanner.nextLine();

        foodRepository.findFood(foodName).ifPresentOrElse(
                food -> {
                    displayFoodInfo(food);
                    userProfile.addFoodConsumption(food);
                    displayCurrentTotals();
                    checkTargets();
                },
                () -> System.out.println("Food not found in database."));
    }

    private void checkTargets() {
        UserTarget target = userProfile.getUserTarget();
        UserProgress progress = userProfile.getUserProgress();

        boolean exceeded = false;
        if (progress.getCalories() > target.getCalorieTarget()) {
            System.out.println("Warning: You have exceeded your calorie target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        if (progress.getProtein() > target.getProteinTarget()) {
            System.out.println("Warning: You have exceeded your protein target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        if (progress.getCarbs() > target.getCarbsTarget()) {
            System.out.println("Warning: You have exceeded your carbs target!");
            SoundPlayer.playSound();
            exceeded = true;
        }
        if (progress.getFat() > target.getFatTarget()) {
            System.out.println("Warning: You have exceeded your fat target!");
            SoundPlayer.playSound();
            exceeded = true;
        }

        if (!exceeded) {
            System.out.println("You're within your targets. Good job!");
        }
    }

    private void displayFoodInfo(Food food) {
        System.out.println("\nNutrition Information for " + food.getName() + ":");
        System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
                food.getCalories(), food.getProtein(), food.getCarbs(), food.getFat());
    }

    private void displayCurrentTotals() {
        UserProgress progress = userProfile.getUserProgress();
        System.out.println("\nYour Current Totals:");
        System.out.printf("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg\n",
                progress.getCalories(), progress.getProtein(),
                progress.getCarbs(), progress.getFat());
    }

    private void startExerciseTimer() {
        UserProgress progress = userProfile.getUserProgress();
        UserTarget target = userProfile.getUserTarget();

        if (progress.getExerciseTime() >= target.getExerciseTarget()) {
            handleExceededExerciseTarget();
            return;
        }

        System.out.print("Enter exercise duration (minutes): ");
        int minutes = scanner.nextInt();
        scanner.nextLine();

        if (minutes <= 0) {
            System.out.println("Invalid duration. Timer not started.");
            return;
        }

        runExerciseTimer(minutes);
        userProfile.addExerciseTime(minutes);
        System.out.println("Total exercise time: " + progress.getExerciseTime() + " minutes");
    }

    private void runExerciseTimer(int minutes) {
        System.out.println("Starting exercise timer for " + minutes + " minutes...");
        try {
            for (int i = minutes * 60; i > 0; i--) {
                if (i % 60 == 0) {
                    System.out.println("Time remaining: " + (i / 60) + " minutes");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Time's up! Exercise completed.");
            SoundPlayer.playSound();
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }

    private void showReport() {
        NutritionReport report = userProfile.generateReport();
        report.display();
    }

    private void handleExceededExerciseTarget() {
        System.out.println("You've already reached your exercise target!");
        System.out.print("Do you want to increase your exercise target? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("yes")) {
            System.out.print("Enter new exercise target (minutes): ");
            int newTarget = scanner.nextInt();
            scanner.nextLine();
            userProfile.getUserTarget().setExerciseTarget(newTarget);
            userProfile.saveTargets();
            System.out.println("Exercise target updated successfully!");
            SoundPlayer.playSound();
        }
        showReport();
    }
}

// Domain Models (unchanged from previous)
class Food {
    private final String name;
    private final int calories;
    private final double protein;
    private final double carbs;
    private final double fat;

    public Food(String name, int calories, double protein, double carbs, double fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }
}

class UserTarget {
    private int calorieTarget;
    private double proteinTarget;
    private double carbsTarget;
    private double fatTarget;
    private int exerciseTarget;

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    public void setProteinTarget(double proteinTarget) {
        this.proteinTarget = proteinTarget;
    }

    public void setCarbsTarget(double carbsTarget) {
        this.carbsTarget = carbsTarget;
    }

    public void setFatTarget(double fatTarget) {
        this.fatTarget = fatTarget;
    }

    public void setExerciseTarget(int exerciseTarget) {
        this.exerciseTarget = exerciseTarget;
    }

    public int getCalorieTarget() {
        return calorieTarget;
    }

    public double getProteinTarget() {
        return proteinTarget;
    }

    public double getCarbsTarget() {
        return carbsTarget;
    }

    public double getFatTarget() {
        return fatTarget;
    }

    public int getExerciseTarget() {
        return exerciseTarget;
    }
}

class UserProgress {
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private int exerciseTime;

    public void addFood(Food food) {
        calories += food.getCalories();
        protein += food.getProtein();
        carbs += food.getCarbs();
        fat += food.getFat();
    }

    public void addExerciseTime(int minutes) {
        exerciseTime += minutes;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public int getExerciseTime() {
        return exerciseTime;
    }

    @Override
    public String toString() {
        return String.format("Calories: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg, Exercise: %dmin",
                calories, protein, carbs, fat, exerciseTime);
    }
}

// Report Generator
class NutritionReport {
    private final UserTarget target;
    private final UserProgress progress;

    public NutritionReport(UserTarget target, UserProgress progress) {
        this.target = target;
        this.progress = progress;
    }

    public void display() {
        System.out.println("\n--- Nutrition & Fitness Report ---");
        displayProgressVsTargets();
        displayGraphicalRepresentation();
        displayStatus();
    }

    private void displayProgressVsTargets() {
        System.out.println("\n[Current Progress vs Targets]");
        System.out.println("Calories: " + progress.getCalories() + "/" + target.getCalorieTarget());
        System.out.println("Protein: " + progress.getProtein() + "g/" + target.getProteinTarget() + "g");
        System.out.println("Carbs: " + progress.getCarbs() + "g/" + target.getCarbsTarget() + "g");
        System.out.println("Fat: " + progress.getFat() + "g/" + target.getFatTarget() + "g");
        System.out.println("Exercise: " + progress.getExerciseTime() + "min/" + target.getExerciseTarget() + "min");
    }

    private void displayGraphicalRepresentation() {
        System.out.println("\n[Graphical Representation]");
        printBarChart("Calories", progress.getCalories(), target.getCalorieTarget());
        printBarChart("Protein", progress.getProtein(), target.getProteinTarget());
        printBarChart("Carbs", progress.getCarbs(), target.getCarbsTarget());
        printBarChart("Fat", progress.getFat(), target.getFatTarget());
        printBarChart("Exercise", progress.getExerciseTime(), target.getExerciseTarget());
    }

    private void printBarChart(String label, double progress, double target) {
        int maxBars = 50;
        int bars = (int) ((progress / target) * maxBars);
        bars = Math.min(bars, maxBars);
        System.out.print(label + ": [");
        System.out.print("=".repeat(bars));
        System.out.print(" ".repeat(maxBars - bars));
        System.out.printf("] %.1f/%.1f\n", progress, target);
    }

    private void displayStatus() {
        System.out.println("\n[Status]");
        new TargetChecker(target, progress).checkExceedingTargets();
        if (progress.getExerciseTime() >= target.getExerciseTarget()) {
            System.out.println("You've met your exercise goal! Great job!");
        } else {
            System.out.println("Exercise remaining: " +
                    (target.getExerciseTarget() - progress.getExerciseTime()) + " minutes");
        }
    }
}

// Target Checker
class TargetChecker {
    private final UserTarget target;
    private final UserProgress progress;

    public TargetChecker(UserTarget target, UserProgress progress) {
        this.target = target;
        this.progress = progress;
    }

    public void checkExceedingTargets() {
        boolean exceeded = false;

        exceeded |= checkExceeded("calorie", progress.getCalories(), target.getCalorieTarget());
        exceeded |= checkExceeded("protein", progress.getProtein(), target.getProteinTarget());
        exceeded |= checkExceeded("carbs", progress.getCarbs(), target.getCarbsTarget());
        exceeded |= checkExceeded("fat", progress.getFat(), target.getFatTarget());

        if (!exceeded) {
            System.out.println("You're within your targets. Good job!");
        }
    }

    private boolean checkExceeded(String nutrient, double current, double target) {
        if (current > target) {
            System.out.printf("Warning: You have exceeded your %s target!%n", nutrient);
            return true;
        }
        return false;
    }
}