package Test;

import org.junit.jupiter.api.Test;

import src.HealthFactor.TDEECalculator;

import static org.junit.jupiter.api.Assertions.*;

public class TDEECalculatorTest {

    @Test
    public void testTDEECalculation() {
        TDEECalculator tdeeCalculator = new TDEECalculator(1500, 1.55); // BMR and moderate activity level
        double result = tdeeCalculator.calculate();
        assertEquals(2325, result, 0.01, "TDEE calculation failed.");
    }
}
