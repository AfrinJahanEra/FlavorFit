package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.BMIStrategy;
import src.Exercise.BodyPartStrategy;
import src.Exercise.ExerciseStrategy;
import src.Exercise.ExerciseStrategyFactory;
import src.Exercise.HealthConditionStrategy;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseStrategyFactoryTest {

    @Test
    public void testBMIStrategy() {
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();
        ExerciseStrategy strategy = factory.getStrategy("bmi");
        assertTrue(strategy instanceof BMIStrategy, "Should return an instance of BMIStrategy.");
    }

    @Test
    public void testBodyPartStrategy() {
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();
        ExerciseStrategy strategy = factory.getStrategy("bodypart");
        assertTrue(strategy instanceof BodyPartStrategy, "Should return an instance of BodyPartStrategy.");
    }

    @Test
    public void testHealthConditionStrategy() {
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();
        ExerciseStrategy strategy = factory.getStrategy("healthcondition");
        assertTrue(strategy instanceof HealthConditionStrategy, "Should return an instance of HealthConditionStrategy.");
    }

    @Test
    public void testInvalidStrategy() {
        ExerciseStrategyFactory factory = new ExerciseStrategyFactory();
        ExerciseStrategy strategy = factory.getStrategy("invalid");
        assertNull(strategy, "Invalid strategy key should return null.");
    }

    private void assertNotNull(ExerciseStrategy strategy, String bmiStrategy_should_not_be_null) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}