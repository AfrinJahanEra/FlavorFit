package src.User;

import java.util.Scanner;
import src.Utils.ConsoleUI;

public abstract class BaseFeature {
    protected final Scanner scanner = new Scanner(System.in);

    protected static final String RESET = "\u001B[0m";
    protected static final String YELLOW = "\u001B[33m";
    protected static final String BOLD = "\u001B[1m";
    protected static final String BLUE = "\u001B[34m";
    

    public abstract String getTitle();
    public abstract void display();
    
    protected void displayMenuUntilExit(String title, String[] options, Runnable[] handlers) {
        if (options == null || handlers == null || options.length != handlers.length) {
            throw new IllegalArgumentException("Options and handlers arrays must be non-null and of equal length");
        }
        
        while (true) {
            ConsoleUI.clearScreen();
            System.out.println(YELLOW+BOLD+"\n                                          ══════════════════════════════════════════ " + title + " ═════════════════════════════════════════════════"+RESET);
            System.out.println("\n \n");
            for (int i = 0; i < options.length; i++) {
                System.out.println(YELLOW+BOLD+"                                                                                     "+(i + 1) + ". " + options[i]+RESET);
            }
            
            System.out.println(YELLOW+BOLD+"                                                                                     "+(options.length + 1) + ". Back to Main Menu"+RESET);
            System.out.println("\n \n");
            
            int choice = ConsoleUI.getIntInput("Choose an option: ", 1, options.length + 1);
            
            if (choice == options.length + 1) {
                return; // Exit this menu
            }
            
            handlers[choice - 1].run();
            ConsoleUI.pressAnyKeyToContinue();
        }
    }
    
}

// package src.User;

// import java.util.Scanner;
// import src.Utils.ConsoleUI;

// public abstract class BaseFeature {
//     // ANSI Color Codes
//     protected static final String RESET = "\u001B[0m";
//     protected static final String YELLOW = "\u001B[33m";
//     protected static final String BOLD = "\u001B[1m";
//     protected static final String BLUE = "\u001B[34m";
    
//     protected final Scanner scanner = new Scanner(System.in);

//     public abstract String getTitle();
//     public abstract void display();
    
//     protected void displayMenuUntilExit(String title, String[] options, Runnable[] handlers) {
//         if (options == null || handlers == null || options.length != handlers.length) {
//             throw new IllegalArgumentException("Options and handlers arrays must be non-null and of equal length");
//         }
        
//         while (true) {
//             ConsoleUI.clearScreen();
//             printMenuHeader(title);
            
//             for (int i = 0; i < options.length; i++) {
//                 System.out.printf("%70s%d. %s%s%s\n", "", 
//                                 (i + 1), 
//                                 BLUE, options[i], RESET);
//             }
            
//             System.out.printf("%70s%d. %sBack to Main Menu%s\n\n", "",
//                             (options.length + 1),
//                             YELLOW, RESET);
            
//             int choice = ConsoleUI.getIntInput(YELLOW + "Choose an option: " + RESET, 
//                                              1, options.length + 1);
            
//             if (choice == options.length + 1) {
//                 return; // Exit this menu
//             }
            
//             handlers[choice - 1].run();
//             ConsoleUI.pressAnyKeyToContinue();
//         }
//     }
    
//     private void printMenuHeader(String title) {
//         String headerLine = "══════════════════════════════════════════ " + 
//                           title + 
//                           " ══════════════════════════════════════════";
        
//         System.out.println("\n");
//         System.out.println(YELLOW + BOLD + String.format("%70s", "") + headerLine + RESET);
//         System.out.println("\n");
//     }
// }