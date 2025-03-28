package src.HealthFactor;

import java.util.Scanner;
import src.User.User;

public class HealthFactorMain {

    private final User user;

    // Constructor to accept the User object
    public HealthFactorMain(User user) {
        this.user = user;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Choose the health metric
        System.out.println("Select Health Metric to Calculate:");
        System.out.println("1. BMI");
        System.out.println("2. BMR");
        System.out.println("3. IBW");
        System.out.println("4. Hydration Requirements");
        System.out.println("5. Heart Rate Metrics");
        System.out.println("6. TDEE");
        System.out.print("Enter your choice (1-6): ");
        int choice = scanner.nextInt();

        double result = 0;

        // Execute according to the selected choice
        switch (choice) {
            case 1: // BMI
                double weight = user.getWeight();
                double height = user.getHeight(); // Assuming height is in meters
                HealthMetricCalculator bmiCalculator = new BMICalculator(weight, height);
                HealthMetricService bmiService = new HealthMetricService(bmiCalculator);
                result = bmiService.calculateMetric();
                System.out.println("Your BMI is: " + result);
                break;

            case 2: // BMR
                char gender = user.getGender().charAt(0); // Assuming gender is "M" or "F"
                HealthMetricCalculator bmrCalculator = new BMRCalculator(user.getWeight(), user.getHeight() * 100, user.getAge(), gender);
                HealthMetricService bmrService = new HealthMetricService(bmrCalculator);
                result = bmrService.calculateMetric();
                System.out.println("Your BMR is: " + result);
                break;

            case 3: // IBW
                gender = user.getGender().charAt(0); // Assuming gender is "M" or "F"
                HealthMetricCalculator ibwCalculator = new IBWCalculator(user.getHeight() * 100, gender);
                HealthMetricService ibwService = new HealthMetricService(ibwCalculator);
                result = ibwService.calculateMetric();
                System.out.println("Your Ideal Body Weight (IBW) is: " + result);
                break;

            case 4: // Hydration Requirements
                HealthMetricCalculator hydrationCalculator = new HydrationCalculator(user.getWeight());
                HealthMetricService hydrationService = new HealthMetricService(hydrationCalculator);
                result = hydrationService.calculateMetric();
                System.out.println("Your daily hydration requirement is: " + result + " liters");
                break;

            case 5: // Heart Rate Metrics
                HealthMetricCalculator heartRateCalculator = new HeartRateCalculator(user.getAge(), 70); // Assuming resting heart rate is 70
                HealthMetricService heartRateService = new HealthMetricService(heartRateCalculator);
                result = heartRateService.calculateMetric();
                System.out.println("Your target heart rate is: " + result);
                break;

            case 6: // TDEE
                System.out.print("Enter activity level multiplier (e.g., 1.2, 1.55, 1.9): ");
                double activityLevel = scanner.nextDouble();
                HealthMetricCalculator tdeeCalculator = new TDEECalculator(result, activityLevel); // Assuming BMR is already calculated
                HealthMetricService tdeeService = new HealthMetricService(tdeeCalculator);
                result = tdeeService.calculateMetric();
                System.out.println("Your Total Daily Energy Expenditure (TDEE) is: " + result);
                break;

            default:
                System.out.println("Invalid choice.");
        }

    }
}