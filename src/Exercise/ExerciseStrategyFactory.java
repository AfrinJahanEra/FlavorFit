package src.Exercise;

import java.util.HashMap;
import java.util.Map;

import src.Interface.ExerciseStrategy;

public class ExerciseStrategyFactory {
    private final Map<String, ExerciseStrategy> strategies = new HashMap<>();

    public ExerciseStrategyFactory() {
        strategies.put("bmi", new BMIStrategy());
        strategies.put("bodypart", new BodyPartStrategy());
        strategies.put("healthcondition", new HealthConditionStrategy());
    }

    public ExerciseStrategy getStrategy(String key) {
        return strategies.getOrDefault(key.toLowerCase(), null);
    }
}
