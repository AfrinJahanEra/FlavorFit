package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.Exercise;
import src.Exercise.HealthConditionStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HealthConditionStrategyTest {

    @Test
    public void testPregnancyCondition() {
        HealthConditionStrategy strategy = new HealthConditionStrategy();
        List<Exercise> exercises = strategy.getExercises("pregnancy");
        assertEquals(2, exercises.size());
        assertEquals("Prenatal Yoga", exercises.get(0).getName());
        assertEquals("Walking", exercises.get(1).getName());
    }

    @Test
    public void testHeartCondition() {
        HealthConditionStrategy strategy = new HealthConditionStrategy();
        List<Exercise> exercises = strategy.getExercises("heart");
        assertEquals(1, exercises.size());
        assertEquals("Low-Intensity Cardio", exercises.get(0).getName());
    }
    @Test
    public void testBackCondition() {
        HealthConditionStrategy strategy = new HealthConditionStrategy();
        List<Exercise> exercises = strategy.getExercises("back");
        assertEquals(1, exercises.size());
        assertEquals("Back Stretches", exercises.get(0).getName());
    }
}