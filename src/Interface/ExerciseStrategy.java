package src.Interface;

import java.util.List;
import src.Exercise.Exercise;

public interface ExerciseStrategy {
    List<Exercise> getExercises(String input);
}