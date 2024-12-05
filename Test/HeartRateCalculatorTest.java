package Test;

import org.junit.jupiter.api.Test;

import src.HealthFactor.HeartRateCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class HeartRateCalculatorTest {

    @Test
    public void testHeartRateCalculation() {
        HeartRateCalculator heartRateCalculator = new HeartRateCalculator(30, 70);
        double result = heartRateCalculator.calculate();
        assertEquals(154.0, result, 0.01, "Heart rate calculation failed.");
    }
}
