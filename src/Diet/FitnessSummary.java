package src.Diet;

import java.time.Duration;

public class FitnessSummary {
    private final Duration totalExerciseTime;

    public FitnessSummary(Duration totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }

    public Duration getTotalExerciseTime() { return totalExerciseTime; }
}