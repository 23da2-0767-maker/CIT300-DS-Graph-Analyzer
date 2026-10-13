import java.util.Scanner;

/** Shared helper for safe console input (prevents crashes on invalid input). */
public class InputUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    private InputUtil() { }

    /** Keeps asking until the user enters a valid whole number. */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /** Keeps asking until the number is between min and max (inclusive). */
    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    /** Asks a yes/no question. */
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String answer = SCANNER.nextLine().trim().toLowerCase();
            if (answer.equals("y")) return true;
            if (answer.equals("n")) return false;
            System.out.println("Please type y or n.");
        }
    }
}
