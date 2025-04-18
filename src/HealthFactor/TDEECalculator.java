package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class TDEECalculator implements HealthMetricCalculator {
    private final double bmr;
    private final double activityLevel; // Multiplier based on activity (e.g., sedentary = 1.2, moderate = 1.55, active = 1.9)

    public TDEECalculator(double bmr, double activityLevel) {
        this.bmr = bmr;
        this.activityLevel = activityLevel;
    }

    @Override
    public double calculate() {
        return bmr * activityLevel;
    }
}
