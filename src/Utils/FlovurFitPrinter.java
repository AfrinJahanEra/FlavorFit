package src.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlovurFitPrinter {
    
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";
    
    public void FlovurFitPrint() {
        String fileName = "flovurfit_art.txt";


        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            
            System.out.println("\n");
            
            while ((line = reader.readLine()) != null) {
                // Apply different colors to different sections
                String coloredLine;
                if (lineNumber < 5) {
                    coloredLine = PURPLE + line + RESET;  // Title
                } else if (lineNumber < 12) {
                    coloredLine = CYAN + line + RESET;    // Fancy letters
                } else if (lineNumber < 14) {
                    coloredLine = GREEN + BOLD + line + RESET;  // Tagline
                } else {
                    coloredLine = YELLOW + line + RESET;  // Cow
                }
                
                System.out.println(coloredLine);
                lineNumber++;
                
                // Add small delay for dramatic effect
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Continue without delay if interrupted
                }
            }
            
            System.out.println("\n" + RED + BOLD + "Welcome to FlovurFit!" + RESET + "\n");
            
        } catch (IOException e) {
            // If file reading fails, print embedded version
            printEmbeddedArt();
        }
    }
    
    private static void printEmbeddedArt() {
        String[] embeddedArt = {

            "",
            CYAN + "                             ███████╗██╗      ██████╗ ██╗   ██║██╗   ██╗██████╗ ███████╗██╗████████╗" + RESET,
            CYAN + "                             ██╔════╝██║     ██╔═══██╗██║   ██║██║   ██║██╔══██╗██╔════╝██║╚══██╔══╝" + RESET,
            CYAN + "                             █████╗  ██║     ██║   ██║██║   ██║██║   ██║██████╔╝█████╗  ██║   ██║    " + RESET,
            CYAN + "                             ██╔══╝  ██║     ██║   ██║ ██║ ██║ ██║   ██║██╔══██╗██╔══╝  ██║   ██║   " + RESET,
            CYAN + "                             ██║     ███████╗╚██████╔╝ ╚████╔╝ ╚██████╔╝██║  ██║██║     ██║   ██║    " + RESET,
            CYAN + "                             ╚═╝     ╚══════╝ ╚═════╝   ╚═══╝   ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝   ╚═╝   " + RESET,
            "",

        };
        
        System.out.println("\n");
        for (String line : embeddedArt) {
            System.out.println(line);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Continue without delay if interrupted
            }
        }
        System.out.println("\n" + RED + BOLD + "                                                    Welcome to FlovurFit!" + RESET + "\n");
    }
}