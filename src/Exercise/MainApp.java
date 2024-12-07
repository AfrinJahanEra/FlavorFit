package src.Exercise;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();

        System.out.println("Welcome to Exercise Recommendation System");
        System.out.println("Choose an option: 1. BMI-Based 2. Body Part-Based 3. Health Condition-Based");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        ExerciseStrategy strategy = null;
        String input = "";

        switch (choice) {
            case 1:
                strategy = factory.getStrategy("bmi");
                System.out.println("Enter your height (m) and weight (kg) as 'height,weight':");
                input = scanner.nextLine();
                break;
            case 2:
                strategy = factory.getStrategy("bodypart");
                System.out.println("Enter the body part:");
                input = scanner.nextLine();
                break;
            case 3:
                strategy = factory.getStrategy("healthcondition");
                System.out.println("Enter your health condition:");
                input = scanner.nextLine();
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