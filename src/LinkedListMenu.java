import java.util.Scanner;

public class LinkedListMenu {
    private static SinglyLinkedList list = new SinglyLinkedList();

    public static void run(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--------------- LINKED LIST OPERATIONS ---------------");
            System.out.println("1. Insert at Beginning");
            System.out.println("2. Insert at End");
            System.out.println("3. Insert at Position");
            System.out.println("4. Delete by Value");
            System.out.println("5. Search");
            System.out.println("6. Display");
            System.out.println("7. Return to Main Menu");
            choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    list.insertAtBeginning(readInt(sc, "Enter value: "));
                    System.out.println("Inserted.");
                }
                case 2 -> {
                    list.insertAtEnd(readInt(sc, "Enter value: "));
                    System.out.println("Inserted.");
                }
                case 3 -> {
                    int pos = readInt(sc, "Enter position (1-" + (list.getSize() + 1) + "): ");
                    int val = readInt(sc, "Enter value: ");
                    System.out.println(list.insertAtPosition(pos, val) ? "Inserted." : "Invalid position!");
                }
                case 4 -> {
                    if (list.isEmpty()) {
                        System.out.println("List is empty. Nothing to delete.");
                    } else {
                        int val = readInt(sc, "Enter value to delete: ");
                        System.out.println(list.deleteByValue(val) ? "Deleted." : "Value not found.");
                    }
                }
                case 5 -> {
                    if (list.isEmpty()) {
                        System.out.println("List is empty.");
                    } else {
                        int val = readInt(sc, "Enter value to search: ");
                        int pos = list.search(val);
                        if (pos == -1) System.out.println("Not found.");
                        else System.out.println("Found at position " + pos);
                        System.out.println("Steps taken: " + list.getLastSearchSteps());
                    }
                }
                case 6 -> list.display();
                case 7 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice! Enter 1-7.");
            }
        } while (choice != 7);
    }

    // Input validation: number mattum accept pannum, crash aagaadhu
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }
    }
}