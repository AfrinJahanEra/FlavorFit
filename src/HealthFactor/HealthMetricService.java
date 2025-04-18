package src.HealthFactor;

import src.Interface.HealthMetricCalculator;

public class HealthMetricService {
    private final HealthMetricCalculator calculator;

    public HealthMetricService(HealthMetricCalculator calculator) {
        this.calculator = calculator;
    }

    public double calculateMetric() {
        return calculator.calculate();
    }
}
