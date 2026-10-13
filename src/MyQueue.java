import java.util.Scanner;

/** Queue implemented using a circular array (FIFO). Member 2 */
public class MyQueue {
    private int[] data;
    private int front = 0;
    private int size = 0;
    private int steps = 0;    // operation counter for Performance Comparison

    public MyQueue(int capacity) { data = new int[capacity]; }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == data.length; }
    public int getSteps()    { return steps; }

    public void enqueue(int value) {
        if (isFull()) { System.out.println("Queue Overflow! Queue is full."); return; }
        int rear = (front + size) % data.length;
        data[rear] = value;
        size++;
        steps++;
        System.out.println(value + " enqueued.");
    }

    public void dequeue() {
        if (isEmpty()) { System.out.println("Queue Underflow! Queue is empty, cannot dequeue."); return; }
        System.out.println(data[front] + " dequeued.");
        front = (front + 1) % data.length;
        size--;
        steps++;
    }

    public void peek() {
        if (isEmpty()) { System.out.println("Queue is empty, no front element."); return; }
        System.out.println("Front element: " + data[front]);
        steps++;
    }

    public void display() {
        if (isEmpty()) { System.out.println("Queue is empty."); return; }
        System.out.print("Queue (front -> rear): ");
        for (int i = 0; i < size; i++) System.out.print(data[(front + i) % data.length] + " ");
        System.out.println();
    }

    /** Submenu - call this from the main menu (option 3) */
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--------------- QUEUE OPERATIONS ---------------");
            System.out.println("1. Enqueue\n2. Dequeue\n3. Peek/Front\n4. Display\n5. Return to Main Menu");
            choice = MyStack.readInt(sc, "Enter your choice: ");
            switch (choice) {
                case 1: enqueue(MyStack.readInt(sc, "Enter value to enqueue: ")); break;
                case 2: dequeue(); break;
                case 3: peek(); break;
                case 4: display(); break;
                case 5: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice! Enter 1-5.");
            }
        } while (choice != 5);
    }
}