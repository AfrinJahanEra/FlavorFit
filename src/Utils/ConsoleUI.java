package src.Utils;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import src.Exercise.Exercise;

public final class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleUI() {}

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        System.out.println("\n═════  " + title + " ═════ ");
    }

    public static void printOption(int number, String text) {
        System.out.printf("%d. %s%n", number, text);
    }


    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) return input;
                System.out.printf("Please enter between %d and %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        }
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static List<String> getCommaSeparatedInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return Arrays.stream(input.split(","))
                   .map(String::trim)
                   .collect(Collectors.toList());
    }

    public static void printExercise(Exercise exercise) {
        System.out.printf("- %s (%s)%n", exercise.getName(), exercise.getCategory());
    }

    public static void printInstructions(String instructions) {
        System.out.println("\nINSTRUCTIONS:");
        System.out.println(instructions);
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void showNotification(String message) {
        System.out.println("\nNOTIFICATION: " + message);
    }
}