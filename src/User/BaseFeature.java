package src.User;

import java.util.Scanner;
import src.Utils.ConsoleUI;

public abstract class BaseFeature {
    protected final Scanner scanner = new Scanner(System.in);
    

    public abstract String getTitle();
    public abstract void display();
    
    protected void displayMenuUntilExit(String title, String[] options, Runnable[] handlers) {
        if (options == null || handlers == null || options.length != handlers.length) {
            throw new IllegalArgumentException("Options and handlers arrays must be non-null and of equal length");
        }
        
        while (true) {
            System.out.println("\n--- " + title + " ---");
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.println((options.length + 1) + ". Back to Main Menu");
            
            int choice = ConsoleUI.getIntInput("Choose an option: ", 1, options.length + 1);
            
            if (choice == options.length + 1) {
                return; // Exit this menu
            }
            
            handlers[choice - 1].run();
            ConsoleUI.pressAnyKeyToContinue();
        }
    }
    
}