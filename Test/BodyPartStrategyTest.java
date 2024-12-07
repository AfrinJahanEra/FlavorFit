package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.BodyPartStrategy;
import src.Exercise.Exercise;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BodyPartStrategyTest {

    @Test
    public void testLegExercises() {
        BodyPartStrategy strategy = new BodyPartStrategy();
        List<Exercise> exercises = strategy.getExercises("leg");
        assertEquals(2, exercises.size());
        assertEquals("Squats", exercises.get(0).getName());
        assertEquals("Lunges", exercises.get(1).getName());
    }
    
    @Test
    public void testArmExercises() {
        BodyPartStrategy strategy = new BodyPartStrategy();
        List<Exercise> exercises = strategy.getExercises("arm");
        assertEquals(2, exercises.size());
        assertEquals("Bicep Curls", exercises.get(0).getName());
        assertEquals("Push Ups", exercises.get(1).getName());
    }

    @Test
    public void testDefaultStretching() {
        BodyPartStrategy strategy = new BodyPartStrategy();
        List<Exercise> exercises = strategy.getExercises("head");
        assertEquals(1, exercises.size());
        assertEquals("Stretching", exercises.get(0).getName());
    }
}