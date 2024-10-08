package Healthcare;
import java.util.Scanner;

public class Validation {
    
    private final Scanner scanner;

    public Validation(Scanner scanner) {
        this.scanner = scanner;
    }

    public String valString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.trim().length() > 0) {
                return input;
            } else {
                System.out.println("\nERR0R! Invalid Input");
            }
        }
    }

    public int valInt(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("\nERR0R! Invalid Input, Please enter a number between " + min + " and " + max + " :");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERR0R! Invalid Input, Please enter a number between " + min + " and " + max + " :");
            }
        }
    }
}

