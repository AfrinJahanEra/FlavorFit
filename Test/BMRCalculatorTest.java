package Test;

import org.junit.jupiter.api.Test;

import src.HealthFactor.BMRCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class BMRCalculatorTest {

    @Test
    public void testBMRCalculation_Male() {
        BMRCalculator bmrCalculator = new BMRCalculator(70, 175, 25, 'M');
        double result = bmrCalculator.calculate();
        assertEquals(1724.0520000000001, result, 0.01, "BMR calculation failed for male inputs.");
    }

    @Test
    public void testBMRCalculation_Female() {
        BMRCalculator bmrCalculator = new BMRCalculator(55, 160, 30, 'F');
        double result = bmrCalculator.calculate();
        assertEquals(1321.9579999999999, result, 0.01, "BMR calculation failed for female inputs.");
    }

    @Test
    public void testBMRCalculation_InvalidGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BMRCalculator(70, 175, 25, 'X').calculate();
        });
        assertEquals("Invalid gender. Use 'M' for male or 'F' for female.", exception.getMessage());
    }
}
