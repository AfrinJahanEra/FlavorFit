package Test;

import org.junit.jupiter.api.Test;

import src.HealthFactor.HydrationCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class HydrationCalculatorTest {

    @Test
    public void testHydrationCalculation() {
        HydrationCalculator hydrationCalculator = new HydrationCalculator(70);
        double result = hydrationCalculator.calculate();
        assertEquals(2.31, result, 0.01, "Hydration calculation failed.");
    }
}
