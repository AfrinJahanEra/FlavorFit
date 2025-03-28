package src.VirtualNutritionist;

import java.util.List;

public class User {
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private List<String> medicalConditions;
    private List<String> allergies;
    private String activityLevel;

    public User(String name, int age, String gender, double weight, double height, 
                List<String> medicalConditions, List<String> allergies, String activityLevel) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.medicalConditions = medicalConditions;
        this.allergies = allergies;
        this.activityLevel = activityLevel;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public List<String> getMedicalConditions() { return medicalConditions; }
    public List<String> getAllergies() { return allergies; }
    public String getActivityLevel() { return activityLevel; }
}

