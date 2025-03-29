package src.Exercise;

import java.util.List;
import src.User.BaseFeature;
import src.User.User;

public class ExerciseMain extends BaseFeature {
    private final User user;
    private final ExerciseStrategyFactory factory;

    public ExerciseMain(User user) {
        this.user = user;
        this.factory = new ExerciseStrategyFactory();
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
            () -> showBmiRecommendations(),
            () -> showBodyPartRecommendations(),
            () -> showHealthConditionRecommendations()
        };

        displayMenu(getTitle(), options, handlers);
    }

    private void showBmiRecommendations() {
        ExerciseStrategy strategy = factory.getStrategy("bmi");
        String input = user.getHeight() + "," + user.getWeight();
        showExerciseRecommendations(strategy, input);
    }

    private void showBodyPartRecommendations() {
        System.out.print("Enter the body part: ");
        String bodyPart = scanner.nextLine();
        ExerciseStrategy strategy = factory.getStrategy("bodypart");
        showExerciseRecommendations(strategy, bodyPart);
    }

    private void showHealthConditionRecommendations() {
        ExerciseStrategy strategy = factory.getStrategy("healthcondition");
        String input = String.join(",", user.getMedicalConditions());
        showExerciseRecommendations(strategy, input);
    }

    private void showExerciseRecommendations(ExerciseStrategy strategy, String input) {
        List<Exercise> exercises = strategy.getExercises(input);
        
        System.out.println("\nRecommended Exercises:");
        for (int i = 0; i < exercises.size(); i++) {
            System.out.println((i + 1) + ". " + exercises.get(i).getName());
        }

        System.out.print("Select an exercise to see instructions (0 to go back): ");
        int choice = getIntInput("", 0, exercises.size());
        
        if (choice > 0) {
            Exercise selected = exercises.get(choice - 1);
            System.out.println("\nInstructions for " + selected.getName() + ":");
            System.out.println(InstructionReader.readInstructions(selected.getDetailsFilePath()));
        }
    }
}