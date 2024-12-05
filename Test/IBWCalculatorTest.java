package Test;

import src.HealthFactor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IBWCalculatorTest {

    @Test
    public void testIBWCalculation_Male() {
        IBWCalculator ibwCalculator = new IBWCalculator(175, 'M');
        double result = ibwCalculator.calculate();
        assertEquals(70.34, result, 0.01, "IBW calculation failed for male.");
    }

    @Test
    public void testIBWCalculation_Female() {
        IBWCalculator ibwCalculator = new IBWCalculator(160, 'F');
        double result = ibwCalculator.calculate();
        assertEquals(52.339999999999996, result, 0.01, "IBW calculation failed for female.");
    }

    @Test
    public void testIBWCalculation_InvalidGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new IBWCalculator(175, 'X').calculate();
        });
        assertEquals("Invalid gender. Use 'M' for male or 'F' for female.", exception.getMessage());
    }
}
