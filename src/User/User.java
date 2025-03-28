package src.User;

import java.util.List;

public class User {
    private final String name;
    private final int age;
    private final String gender;
    private final double weight;
    private final double height;
    private final List<String> medicalConditions;
    private final List<String> allergies;
    private final String activityLevel;

    public User(String name, int age, String gender, double weight, double height, 
               List<String> medicalConditions, List<String> allergies, String activityLevel) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.medicalConditions = List.copyOf(medicalConditions);
        this.allergies = List.copyOf(allergies);
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