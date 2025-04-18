package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class HeartRateCalculator implements HealthMetricCalculator {
    private final int age;
    private final double restingHeartRate;

    public HeartRateCalculator(int age, double restingHeartRate) {
        this.age = age;
        this.restingHeartRate = restingHeartRate;
    }

    @Override
    public double calculate() {
        double maxHeartRate = 220 - age;
        return 0.7 * (maxHeartRate - restingHeartRate) + restingHeartRate; // Target heart rate
    }
}
