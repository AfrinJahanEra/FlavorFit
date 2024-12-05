package Test;

import org.junit.jupiter.api.Test;

import src.HealthFactor.BMICalculator;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorTest {

    @Test
    public void testBMICalculation_ValidInputs() {
        BMICalculator bmiCalculator = new BMICalculator(70, 1.75);
        double result = bmiCalculator.calculate();
        assertEquals(22.86, result, 0.01, "BMI calculation failed for valid inputs.");
    }

    @Test
    public void testBMICalculation_ZeroHeight() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BMICalculator(70, 0).calculate();
        });
        assertEquals("Height must be greater than zero.", exception.getMessage());
    }
}
