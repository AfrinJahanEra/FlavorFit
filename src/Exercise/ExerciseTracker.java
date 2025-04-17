package src.Exercise;

import java.util.List;
import src.FileManager.InstructionReader;
import src.User.BaseFeature;
import src.User.User;
import src.Utils.ConsoleUI;

public class ExerciseTracker extends BaseFeature {
    private final User user;
    private final ExerciseStrategyFactory factory;
    private final InstructionReader instructionReader;

    // Instead of creating dependencies internally:
    public ExerciseTracker(User user) {
        this.user = user;
        this.factory = new ExerciseStrategyFactory();  // Tight coupling
        this.instructionReader = new InstructionReader();  // Tight coupling
    }

    // Consider:
    public ExerciseTracker(User user, ExerciseStrategyFactory factory, InstructionReader reader) {
        this.user = user;
        this.factory = factory;  // Injected dependency
        this.instructionReader = reader;  // Injected dependency
    }

    @Override
    public String getTitle() {
        return "Exercise Recommendation System";
    }

    @Override
    public void display() {
        String[] options = {
            "BMI-Based Recommendations",
            "Body Part-Based Recommendations", 
            "Health Condition-Based Recommendations"
        };

        Runnable[] handlers = {
            this::showBmiRecommendations,
            this::showBodyPartRecommendations,
            this::showHealthConditionRecommendations
        };

        displayMenuUntilExit(getTitle(), options, handlers);
    }

    private void showBmiRecommendations() {
        try {
            ExerciseStrategy strategy = factory.getStrategy("bmi");
            String input = user.getHeight() + "," + user.getWeight();
            showExerciseRecommendations(strategy, input);
        } catch (Exception e) {
            System.out.println("Error getting BMI recommendations: " + e.getMessage());
        }
    }

    private void showBodyPartRecommendations() {
        try {
            System.out.print("Enter the body part: ");
            String bodyPart = scanner.nextLine();
            ExerciseStrategy strategy = factory.getStrategy("bodypart");
            showExerciseRecommendations(strategy, bodyPart);
        } catch (Exception e) {
            System.out.println("Error getting body part recommendations: " + e.getMessage());
        }
    }

    private void showHealthConditionRecommendations() {
        try {
            ExerciseStrategy strategy = factory.getStrategy("healthcondition");
            String input = String.join(",", user.getMedicalConditions());
            showExerciseRecommendations(strategy, input);
        } catch (Exception e) {
            System.out.println("Error getting health condition recommendations: " + e.getMessage());
        }
    }

    private void showExerciseRecommendations(ExerciseStrategy strategy, String input) {
        try {
            List<Exercise> exercises = strategy.getExercises(input);
            
            if (exercises.isEmpty()) {
                System.out.println("\nNo exercises found for your criteria.");
                return;
            }

            System.out.println("\nRecommended Exercises:");
            for (int i = 0; i < exercises.size(); i++) {
                System.out.println((i + 1) + ". " + exercises.get(i).getName());
            }

            System.out.print("\nSelect an exercise to see instructions (0 to go back): ");
            int choice = ConsoleUI.getIntInput("", 0, exercises.size());
            
            if (choice > 0) {
                Exercise selected = exercises.get(choice - 1);
                System.out.println("\nInstructions for " + selected.getName() + ":");
                System.out.println(instructionReader.readInstructions(selected.getDetailsFilePath()));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error showing exercise recommendations: " + e.getMessage());
        }
    }
}