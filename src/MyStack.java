import java.util.Scanner;

/** Stack implemented using an array (LIFO). Member 2 */
public class MyStack {
    private int[] data;
    private int top = -1;     // index of top element
    private int steps = 0;    // operation counter for Performance Comparison

    public MyStack(int capacity) { data = new int[capacity]; }

    public boolean isEmpty() { return top == -1; }
    public boolean isFull()  { return top == data.length - 1; }
    public int getSteps()    { return steps; }

    public void push(int value) {
        if (isFull()) { System.out.println("Stack Overflow! Stack is full."); return; }
        data[++top] = value;
        steps++;
        System.out.println(value + " pushed to stack.");
    }

    public void pop() {
        if (isEmpty()) { System.out.println("Stack Underflow! Stack is empty, cannot pop."); return; }
        System.out.println(data[top--] + " popped from stack.");
        steps++;
    }

    public void peek() {
        if (isEmpty()) { System.out.println("Stack is empty, nothing to peek."); return; }
        System.out.println("Top element: " + data[top]);
        steps++;
    }

    public void display() {
        if (isEmpty()) { System.out.println("Stack is empty."); return; }
        System.out.print("Stack (top -> bottom): ");
        for (int i = top; i >= 0; i--) System.out.print(data[i] + " ");
        System.out.println();
    }

    /** Submenu - call this from the main menu (option 2) */
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--------------- STACK OPERATIONS ---------------");
            System.out.println("1. Push\n2. Pop\n3. Peek\n4. Display\n5. Return to Main Menu");
            choice = readInt(sc, "Enter your choice: ");
            switch (choice) {
                case 1: push(readInt(sc, "Enter value to push: ")); break;
                case 2: pop(); break;
                case 3: peek(); break;
                case 4: display(); break;
                case 5: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice! Enter 1-5.");
            }
        } while (choice != 5);
    }

    // Input validation: keeps asking until user enters a valid integer
    public static int readInt(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            if (!sc.hasNextLine()) return 5;
            String input = sc.nextLine().trim();
            try { return Integer.parseInt(input); }
            catch (NumberFormatException e) { System.out.println("Invalid input! Please enter a number."); }
        }
    }
}