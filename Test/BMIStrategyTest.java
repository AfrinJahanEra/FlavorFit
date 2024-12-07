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


}
