package src.Exercise;

import java.util.ArrayList;
import java.util.List;

public class HealthConditionStrategy implements ExerciseStrategy {
    @Override
    public List<Exercise> getExercises(String input) {
        String condition = input.toLowerCase();
        List<Exercise> exercises = new ArrayList<>();

        if (condition.contains("pregnancy")) {
            exercises.add(new Exercise("Prenatal Yoga", "HealthCondition", "src/files/prenatal_yoga.txt"));
            exercises.add(new Exercise("Walking", "HealthCondition", "src/files/walking.txt"));
        } else if (condition.contains("heart")) {
            exercises.add(new Exercise("Low-Intensity Cardio", "HealthCondition", "src/files/low_intensity_cardio.txt"));
        } else if (condition.contains("back")) {
            exercises.add(new Exercise("Back Stretches", "HealthCondition", "src/files/back_stretches.txt"));
        }
        return exercises;
    }
}