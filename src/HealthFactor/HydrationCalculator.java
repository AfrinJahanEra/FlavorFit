package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class HydrationCalculator implements HealthMetricCalculator {
    private final double weightInKg;

    public HydrationCalculator(double weightInKg) {
        this.weightInKg = weightInKg;
    }

    @Override
    public double calculate() {
        return weightInKg * 0.033; // Water intake in liters
    }
}
