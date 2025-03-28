package src.Diet;

import java.time.Duration;
import java.util.*;

public class InMemoryFitnessTracker implements ExerciseLogger, FitnessReporter {
    private final List<Exercise> exercises = new ArrayList<>();
    private Duration totalExerciseTime = Duration.ZERO;

    @Override
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        totalExerciseTime = totalExerciseTime.plus(exercise.getDuration());
    }

    @Override
    public FitnessSummary getFitnessSummary() {
        return new FitnessSummary(totalExerciseTime);
    }

    @Override
    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }
}