package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class IBWCalculator implements HealthMetricCalculator {
    private final double heightInCm;
    private final char gender; // 'M' for male, 'F' for female

    public IBWCalculator(double heightInCm, char gender) {
        this.heightInCm = heightInCm;
        this.gender = gender;
    }

    @Override
    public double calculate() {
        if (gender == 'M') {
            return 50 + 0.9 * (heightInCm - 152.4);
        } else if (gender == 'F') {
            return 45.5 + 0.9 * (heightInCm - 152.4);
        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }
    }
}
