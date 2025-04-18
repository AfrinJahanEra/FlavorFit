package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class BMRCalculator implements HealthMetricCalculator {
    private final double weightInKg;
    private final double heightInCm;
    private final int ageInYears;
    private final char gender; // 'M' for male, 'F' for female

    public BMRCalculator(double weightInKg, double heightInCm, int ageInYears, char gender) {
        this.weightInKg = weightInKg;
        this.heightInCm = heightInCm;
        this.ageInYears = ageInYears;
        this.gender = gender;
    }

    @Override
    public double calculate() {
        if (gender == 'M') {
            return 88.362 + (13.397 * weightInKg) + (4.799 * heightInCm) - (5.677 * ageInYears);
        } else if (gender == 'F') {
            return 447.593 + (9.247 * weightInKg) + (3.098 * heightInCm) - (4.330 * ageInYears);
        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }
    }
}
