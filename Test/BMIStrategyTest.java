package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.BMIStrategy;
import src.Exercise.Exercise;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BMIStrategyTest {

    @Test
    public void testUnderweightBmi() {
        BMIStrategy strategy = new BMIStrategy();
        List<Exercise> exercises = strategy.getExercises("1.70,50");
        assertEquals(2, exercises.size());
        assertEquals("Light Yoga", exercises.get(0).getName());
        assertEquals("Walking", exercises.get(1).getName());
    }

    @Test
    public void testNormalBmi() {
        BMIStrategy strategy = new BMIStrategy();
        List<Exercise> exercises = strategy.getExercises("1.70,70");
        assertEquals(2, exercises.size());
        assertEquals("Cardio", exercises.get(0).getName());
        assertEquals("Strength Training", exercises.get(1).getName());
    }

    @Test
    public void testOverweightBmi() {
        BMIStrategy strategy = new BMIStrategy();
        List<Exercise> exercises = strategy.getExercises("1.70,90");
        assertEquals(2, exercises.size());
        assertEquals("Low-Impact Aerobics", exercises.get(0).getName());
        assertEquals("Swimming", exercises.get(1).getName());
    }

}
