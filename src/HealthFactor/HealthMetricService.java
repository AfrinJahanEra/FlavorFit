package src.HealthFactor;

public class HealthMetricService {
    private final HealthMetricCalculator calculator;

    public HealthMetricService(HealthMetricCalculator calculator) {
        this.calculator = calculator;
    }

    public double calculateMetric() {
        return calculator.calculate();
    }
}
