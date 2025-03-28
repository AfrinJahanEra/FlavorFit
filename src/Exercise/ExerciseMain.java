package src.Exercise;

import java.util.List;
import java.util.Scanner;
import src.User.User;

public class ExerciseMain {
    private final User user;

    // Constructor to accept the User object
    public ExerciseMain(User user) {
        this.user = user;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();

        System.out.println("Welcome to Exercise Recommendation System");
        System.out.println("Choose an option: 1. BMI-Based 2. Body Part-Based 3. Health Condition-Based");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        ExerciseStrategy strategy = null;
        String input = "";

        switch (choice) {
            case 1: // BMI-Based
                strategy = factory.getStrategy("bmi");
                // Use user's height and weight instead of prompting for input
                input = user.getHeight() + "," + user.getWeight();
                break;

            case 2: // Body Part-Based
                strategy = factory.getStrategy("bodypart");
                System.out.println("Enter the body part:");
                input = scanner.nextLine();
                break;

            case 3: // Health Condition-Based
                strategy = factory.getStrategy("healthcondition");
                // Use user's medical conditions instead of prompting for input
                input = String.join(",", user.getMedicalConditions());
                break;

            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (strategy != null) {
            List<Exercise> exercises = strategy.getExercises(input);
            System.out.println("Recommended Exercises:");
            for (Exercise exercise : exercises) {
                System.out.println("- " + exercise.getName());
            }

            System.out.println("Enter the name of the exercise to see instructions:");
            String exerciseName = scanner.nextLine();
            for (Exercise exercise : exercises) {
                if (exercise.getName().equalsIgnoreCase(exerciseName)) {
                    System.out.println("Instructions:");
                    System.out.println(InstructionReader.readInstructions(exercise.getDetailsFilePath()));
                    break;
                }
            }
        }
    }
}