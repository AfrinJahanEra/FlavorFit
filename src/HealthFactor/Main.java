package src.HealthFactor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
                System.out.print("Enter weight (kg): ");
                double weight = scanner.nextDouble();
                System.out.print("Enter height (m): ");
                double height = scanner.nextDouble();
                HealthMetricCalculator bmiCalculator = new BMICalculator(weight, height);
                HealthMetricService bmiService = new HealthMetricService(bmiCalculator);
                result = bmiService.calculateMetric();
                System.out.println("Your BMI is: " + result);
                break;
                
            case 2: // BMR
                System.out.print("Enter weight (kg): ");
                weight = scanner.nextDouble();
                System.out.print("Enter height (cm): ");
                double heightCm = scanner.nextDouble();
                System.out.print("Enter age (years): ");
                int age = scanner.nextInt();
                System.out.print("Enter gender (M/F): ");
                char gender = scanner.next().charAt(0);
                HealthMetricCalculator bmrCalculator = new BMRCalculator(weight, heightCm, age, gender);
                HealthMetricService bmrService = new HealthMetricService(bmrCalculator);
                result = bmrService.calculateMetric();
                System.out.println("Your BMR is: " + result);
                break;
                
            case 3: // IBW
                System.out.print("Enter height (cm): ");
                heightCm = scanner.nextDouble();
                System.out.print("Enter gender (M/F): ");
                gender = scanner.next().charAt(0);
                HealthMetricCalculator ibwCalculator = new IBWCalculator(heightCm, gender);
                HealthMetricService ibwService = new HealthMetricService(ibwCalculator);
                result = ibwService.calculateMetric();
                System.out.println("Your Ideal Body Weight (IBW) is: " + result);
                break;
                
            case 4: // Hydration Requirements
                System.out.print("Enter weight (kg): ");
                weight = scanner.nextDouble();
                HealthMetricCalculator hydrationCalculator = new HydrationCalculator(weight);
                HealthMetricService hydrationService = new HealthMetricService(hydrationCalculator);
                result = hydrationService.calculateMetric();
                System.out.println("Your daily hydration requirement is: " + result + " liters");
                break;
                
            case 5: // Heart Rate Metrics
                System.out.print("Enter age (years): ");
                age = scanner.nextInt();
                System.out.print("Enter resting heart rate: ");
                double restingHeartRate = scanner.nextDouble();
                HealthMetricCalculator heartRateCalculator = new HeartRateCalculator(age, restingHeartRate);
                HealthMetricService heartRateService = new HealthMetricService(heartRateCalculator);
                result = heartRateService.calculateMetric();
                System.out.println("Your target heart rate is: " + result);
                break;
                
            case 6: // TDEE
                System.out.print("Enter BMR: ");
                double bmr = scanner.nextDouble();
                System.out.print("Enter activity level multiplier (e.g., 1.2, 1.55, 1.9): ");
                double activityLevel = scanner.nextDouble();
                HealthMetricCalculator tdeeCalculator = new TDEECalculator(bmr, activityLevel);
                HealthMetricService tdeeService = new HealthMetricService(tdeeCalculator);
                result = tdeeService.calculateMetric();
                System.out.println("Your Total Daily Energy Expenditure (TDEE) is: " + result);
                break;
                
            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
