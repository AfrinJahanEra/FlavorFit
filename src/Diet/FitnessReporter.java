package src.Diet;

import java.util.List;

public interface FitnessReporter {
    FitnessSummary getFitnessSummary();
    List<Exercise> getExercises();
}
