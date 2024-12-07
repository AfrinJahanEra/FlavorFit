package src.Exercise;

import java.util.ArrayList;
import java.util.List;

public class BodyPartStrategy implements ExerciseStrategy {
    @Override
    public List<Exercise> getExercises(String input) {
        String bodyPart = input.toLowerCase();
        List<Exercise> exercises = new ArrayList<>();

        if (bodyPart.contains("leg")) {
            exercises.add(new Exercise("Squats", "BodyPart", "src/files/squats.txt"));
            exercises.add(new Exercise("Lunges", "BodyPart", "src/files/lunges.txt"));
        } else if (bodyPart.contains("arm")) {
            exercises.add(new Exercise("Bicep Curls", "BodyPart", "src/files/bicep_curls.txt"));
            exercises.add(new Exercise("Push Ups", "BodyPart", "src/files/push_ups.txt"));
        } else {
            exercises.add(new Exercise("Stretching", "BodyPart", "src/files/stretching.txt"));
        }
        return exercises;
    }
}
