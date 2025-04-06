package src.VirtualNutritionist;

public class Meal {
    private String name;
    private String type; // breakfast, lunch, dinner, etc.
    private String medicalCondition;
    private boolean isAllergenFree;

    public Meal(String name, String type, String medicalCondition, boolean isAllergenFree) {
        this.name = name;
        this.type = type;
        this.medicalCondition = medicalCondition;
        this.isAllergenFree = isAllergenFree;
    }

    Meal(String name, String type, String conditions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getMedicalCondition() { return medicalCondition; }
    public boolean isAllergenFree() { return isAllergenFree; }
}

