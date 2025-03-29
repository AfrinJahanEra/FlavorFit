package src.User;

import java.util.Scanner;
public abstract class BaseFeature implements Feature {
    protected final Scanner scanner = new Scanner(System.in);
    
    protected void displayMenu(String title, String[] options, Runnable[] handlers) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println((options.length + 1) + ". Back");
        
        int choice = getIntInput("Choose an option: ", 1, options.length + 1);
        if (choice <= options.length) {
            handlers[choice - 1].run();
        }
    }
    
    protected int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) return input;
            } catch (NumberFormatException e) {}
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max);
        }
    }
}