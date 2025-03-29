package src.HealthFactor;

import src.User.BaseFeature;
import src.User.User;

public class HealthFactorMain extends BaseFeature {
    private final User user;

    public HealthFactorMain(User user) {
        this.user = user;
    }

    @Override
    public String getTitle() {
        return "Health Factor Calculator";
    }

    @Override
    public void display() {
        String[] options = {
            "BMI",
            "BMR",
            "IBW",
            "Hydration Requirements",
            "Heart Rate Metrics",
            "TDEE"
        };

        Runnable[] handlers = {
            () -> calculateBMI(),
            () -> calculateBMR(),
            () -> calculateIBW(),
            () -> calculateHydration(),
            () -> calculateHeartRate(),
            () -> calculateTDEE()
        };

        displayMenu(getTitle(), options, handlers);
    }

    private void calculateBMI() {
        double weight = user.getWeight();
        double height = user.getHeight();
        HealthMetricCalculator bmiCalculator = new BMICalculator(weight, height);
        HealthMetricService bmiService = new HealthMetricService(bmiCalculator);
        double result = bmiService.calculateMetric();
        System.out.println("Your BMI is: " + result);
    }

    private void calculateBMR() {
        char gender = user.getGender().charAt(0);
        HealthMetricCalculator bmrCalculator = new BMRCalculator(
            user.getWeight(), 
            user.getHeight() * 100, 
            user.getAge(), 
            gender
        );
        HealthMetricService bmrService = new HealthMetricService(bmrCalculator);
        double result = bmrService.calculateMetric();
        System.out.println("Your BMR is: " + result);
    }

    private void calculateIBW() {
        char gender = user.getGender().charAt(0);
        HealthMetricCalculator ibwCalculator = new IBWCalculator(
            user.getHeight() * 100, 
            gender
        );
        HealthMetricService ibwService = new HealthMetricService(ibwCalculator);
        double result = ibwService.calculateMetric();
        System.out.println("Your Ideal Body Weight (IBW) is: " + result);
    }

    private void calculateHydration() {
        HealthMetricCalculator hydrationCalculator = new HydrationCalculator(user.getWeight());
        HealthMetricService hydrationService = new HealthMetricService(hydrationCalculator);
        double result = hydrationService.calculateMetric();
        System.out.println("Your daily hydration requirement is: " + result + " liters");
    }

    private void calculateHeartRate() {
        HealthMetricCalculator heartRateCalculator = new HeartRateCalculator(user.getAge(), 70);
        HealthMetricService heartRateService = new HealthMetricService(heartRateCalculator);
        double result = heartRateService.calculateMetric();
        System.out.println("Your target heart rate is: " + result);
    }

    private void calculateTDEE() {
        System.out.print("Enter activity level multiplier (e.g., 1.2, 1.55, 1.9): ");
        double activityLevel = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        // Need to calculate BMR first for TDEE
        char gender = user.getGender().charAt(0);
        HealthMetricCalculator bmrCalculator = new BMRCalculator(
            user.getWeight(), 
            user.getHeight() * 100, 
            user.getAge(), 
            gender
        );
        HealthMetricService bmrService = new HealthMetricService(bmrCalculator);
        double bmr = bmrService.calculateMetric();
        
        HealthMetricCalculator tdeeCalculator = new TDEECalculator(bmr, activityLevel);
        HealthMetricService tdeeService = new HealthMetricService(tdeeCalculator);
        double result = tdeeService.calculateMetric();
        System.out.println("Your Total Daily Energy Expenditure (TDEE) is: " + result);
    }
}