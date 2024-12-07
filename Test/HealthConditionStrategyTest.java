package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.Exercise;
import src.Exercise.HealthConditionStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HealthConditionStrategyTest {

    @Test
    public void testBackCondition() {
        HealthConditionStrategy strategy = new HealthConditionStrategy();
        List<Exercise> exercises = strategy.getExercises("back");
        assertEquals(1, exercises.size());
        assertEquals("Back Stretches", exercises.get(0).getName());
    }
}