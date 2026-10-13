import java.util.Scanner;

/** Entry point: main menu that integrates every component. */
public class Main {
    private static final int STACK_CAPACITY = 10;
    private static final int QUEUE_CAPACITY = 10;

    public static void main(String[] args) {
        Scanner sc = InputUtil.getScanner();   // one shared Scanner for all menus

        ArrayOperations arrayOps = new ArrayOperations();
        SearchOperations searchOps = new SearchOperations(arrayOps);
        MyStack stack = new MyStack(STACK_CAPACITY);
        MyQueue queue = new MyQueue(QUEUE_CAPACITY);
        Graph graph = new Graph();
        PerformanceAnalyzer analyzer = new PerformanceAnalyzer(arrayOps, searchOps, graph);

        int choice;
        do {
            printMainMenu();
            choice = InputUtil.readIntInRange("Enter your choice: ", 1, 9);
            switch (choice) {
                case 1: arrayOps.menu(); break;
                case 2: stack.showMenu(sc); break;
                case 3: queue.showMenu(sc); break;
                case 4: LinkedListMenu.run(sc); break;
                case 5: searchOps.menu(); break;
                case 6: graph.menu(); break;
                case 7: analyzer.menu(); break;
                case 8: analyzer.displayAllResults(); break;
                case 9: System.out.println("Goodbye!"); break;
                default: break;
            }
        } while (choice != 9);
    }

    private static void printMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("DATA STRUCTURE & GRAPH ANALYZER");
        System.out.println("=============================================");
        System.out.println("1. Array Operations");
        System.out.println("2. Stack Operations");
        System.out.println("3. Queue Operations");
        System.out.println("4. Linked List Operations");
        System.out.println("5. Searching Operations");
        System.out.println("6. Graph Operations");
        System.out.println("7. Performance Comparison");
        System.out.println("8. Display All Results");
        System.out.println("9. Exit");
    }
}