package src.User;

import java.io.IOException;
import java.util.List;
import src.Utils.ConsoleUI;


public class App {
    public static void main(String[] args) throws IOException {
        User user = collectUserInfo();
        new MainDashboard(user).display();
    }
    

    private static User collectUserInfo() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("USER REGISTRATION");

        String name = ConsoleUI.getStringInput("Enter your name: ");
        int age = ConsoleUI.getIntInput("Enter your age: ", 1, 120);
        String gender = ConsoleUI.getStringInput("Enter your gender (M/F): ").toUpperCase();
        double weight = ConsoleUI.getDoubleInput("Enter your weight (kg): ");
        double height = ConsoleUI.getDoubleInput("Enter your height (m): ");

        List<String> medicalConditions = ConsoleUI.getCommaSeparatedInput(
            "Enter medical conditions (comma-separated): ");
        List<String> allergies = ConsoleUI.getCommaSeparatedInput(
            "Enter allergies (comma-separated): ");
        String activityLevel = ConsoleUI.getStringInput(
            "Enter activity level (low/medium/high): ");

        return new User(name, age, gender, weight, height,
                      medicalConditions, allergies, activityLevel);
    }
}