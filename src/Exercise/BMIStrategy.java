package src.Exercise;

import java.util.ArrayList;
import java.util.List;
import src.Interface.ExerciseStrategy;

public class BMIStrategy implements ExerciseStrategy {
    @Override
    public List<Exercise> getExercises(String input) {
        // Parse input to extract height and weight
        String[] parts = input.split(",");
        double height = Double.parseDouble(parts[0]);
        double weight = Double.parseDouble(parts[1]);
        double bmi = weight / (height * height);

        List<Exercise> exercises = new ArrayList<>();
        if (bmi < 18.5) {
            exercises.add(new Exercise("Light Yoga", "BMI", "src/files/light_yoga.txt"));
            exercises.add(new Exercise("Walking", "BMI", "src/files/walking.txt"));
        } else if (bmi < 24.9) {
            exercises.add(new Exercise("Cardio", "BMI", "src/files/cardio.txt"));
            exercises.add(new Exercise("Strength Training", "BMI", "src/files/strength_training.txt"));
        } else {
            exercises.add(new Exercise("Low-Impact Aerobics", "BMI", "src/files/low_impact_aerobics.txt"));
            exercises.add(new Exercise("Swimming", "BMI", "src/files/swimming.txt"));
        }
        return exercises;
    }
}