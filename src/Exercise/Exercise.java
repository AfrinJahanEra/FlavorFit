package src.Exercise;

public class Exercise {
    private String name;
    private String category; // e.g., BMI, BodyPart, HealthCondition
    private String detailsFilePath;

    public Exercise(String name, String category, String detailsFilePath) {
        this.name = name;
        this.category = category;
        this.detailsFilePath = detailsFilePath;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDetailsFilePath() {
        return detailsFilePath;
    }
}