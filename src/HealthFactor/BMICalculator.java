package src.HealthFactor;

public class BMICalculator implements HealthMetricCalculator {
    private final double weightInKg;
    private final double heightInMeters;

    public BMICalculator(double weightInKg, double heightInMeters) {
        this.weightInKg = weightInKg;
        this.heightInMeters = heightInMeters;
    }

    @Override
    public double calculate() {
        if (heightInMeters <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero.");
        }
        return weightInKg / (heightInMeters * heightInMeters);
    }
}

