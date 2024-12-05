package src.HealthFactor;
public class Main {
    public static void main(String[] args) {
        // BMI Example
        HealthMetricCalculator bmiCalculator = new BMICalculator(70, 1.75); // weight in kg, height in meters
        HealthMetricService bmiService = new HealthMetricService(bmiCalculator);
        System.out.println("BMI: " + bmiService.calculateMetric());

        // BMR Example
        HealthMetricCalculator bmrCalculator = new BMRCalculator(70, 175, 25, 'M'); // weight in kg, height in cm, age in years, gender
        HealthMetricService bmrService = new HealthMetricService(bmrCalculator);
        System.out.println("BMR: " + bmrService.calculateMetric());
    }
}
