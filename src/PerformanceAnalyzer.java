import java.util.ArrayList;
import java.util.List;

/** Compares searching and graph traversal algorithms by steps and execution time. */
public class PerformanceAnalyzer {
    private static final String ROW = "%-17s %-15s %-10s %-14s %s%n";
    private static final String LINE = "-----------------------------------------------------------------";

    private final ArrayOperations arrayOps;
    private final SearchOperations searchOps;
    private final Graph graph;
    private final List<String> history = new ArrayList<>();

    public PerformanceAnalyzer(ArrayOperations arrayOps, SearchOperations searchOps, Graph graph) {
        this.arrayOps = arrayOps;
        this.searchOps = searchOps;
        this.graph = graph;
    }

    // ---------- Printing helpers ----------
    private void printHeader() {
        System.out.println("\n=============================================");
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=============================================");
        System.out.printf(ROW, "Operation", "Algorithm", "Steps", "Time(ns)", "Result");
        System.out.println(LINE);
    }

    private void printRow(boolean record, String op, String alg, long steps, long time, String result) {
        String line = String.format(ROW, op, alg, steps, time, result);
        System.out.print(line);
        if (record) history.add(line.trim());
    }

    private String searchText(int index) {
        return index == -1 ? "Not found" : "Found at index " + index;
    }

    // ---------- Preparation ----------
    private void prepareArray() {
        if (arrayOps.isEmpty()) {
            System.out.println("Array is empty. Generating 10000 random numbers for the test...");
            arrayOps.generateRandom(10000);
        }
        if (!arrayOps.isSorted()) {
            System.out.println("Binary Search needs sorted data. Sorting the array...");
            arrayOps.sort();
        }
    }

    /** Returns a valid start vertex, or -1 if the graph is empty. */
    private int askStartVertex() {
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph is empty. Add vertices and edges in Graph Operations first.");
            return -1;
        }
        System.out.println("Graph vertices: " + graph.getVertices());
        while (true) {
            int start = InputUtil.readInt("Enter start vertex for BFS/DFS: ");
            if (graph.hasVertex(start)) return start;
            System.out.println("Vertex " + start + " does not exist. Try again.");
        }
    }

    // ---------- Comparisons ----------
    private void searchRows() {
        int key = arrayOps.getData()[arrayOps.getSize() - 1];   // largest value, near the end
        SearchOperations.SearchResult lin = searchOps.linearSearch(key);
        SearchOperations.SearchResult bin = searchOps.binarySearch(key);
        printRow(true, "Search", lin.algorithm, lin.steps, lin.timeNanos, searchText(lin.index));
        printRow(true, "Search", bin.algorithm, bin.steps, bin.timeNanos, searchText(bin.index));
        System.out.println("(Array size " + arrayOps.getSize() + ", key " + key
                + " = last element, worst case for Linear Search)");
    }

    private void graphRows(int start) {
        Graph.TraversalResult bfs = graph.bfs(start);
        Graph.TraversalResult dfs = graph.dfs(start);
        printRow(true, "Graph Traversal", "BFS", bfs.steps, bfs.timeNanos, "Visited " + bfs.order.size() + " vertices");
        printRow(true, "Graph Traversal", "DFS", dfs.steps, dfs.timeNanos, "Visited " + dfs.order.size() + " vertices");
        System.out.println("BFS order: " + bfs.order);
        System.out.println("DFS order: " + dfs.order);
    }

    private void compareSearch() {
        prepareArray();
        printHeader();
        searchRows();
    }

    private void compareGraph() {
        int start = askStartVertex();
        if (start == -1) return;
        printHeader();
        graphRows(start);
    }

    private void fullTable() {
        int start = askStartVertex();      // may be -1: graph rows are skipped
        prepareArray();
        printHeader();
        searchRows();
        if (start != -1) graphRows(start);
    }

    /** Runs Linear vs Binary on increasing sizes to show O(n) vs O(log n). */
    private void scalingTest() {
        int[] sizes = {1000, 10000, ArrayOperations.MAX_SIZE};
        System.out.println("\n=============================================");
        System.out.println("SEARCH SCALING TEST (key = largest value)");
        System.out.println("=============================================");
        System.out.printf("%-10s %-14s %-14s %-16s %s%n", "Size", "Linear steps", "Binary steps", "Linear ns", "Binary ns");
        System.out.println(LINE);
        for (int n : sizes) {
            ArrayOperations temp = new ArrayOperations();   // separate array, user data untouched
            temp.generateRandom(n);
            temp.sort();
            SearchOperations s = new SearchOperations(temp);
            int key = temp.getData()[n - 1];
            SearchOperations.SearchResult lin = s.linearSearch(key);
            SearchOperations.SearchResult bin = s.binarySearch(key);
            String line = String.format("%-10d %-14d %-14d %-16d %d", n, lin.steps, bin.steps, lin.timeNanos, bin.timeNanos);
            System.out.println(line);
            history.add("Scaling n=" + n + ": Linear " + lin.steps + " steps, Binary " + bin.steps + " steps");
        }
        System.out.println("Linear steps grow with n (O(n)). Binary steps grow very slowly (O(log n)).");
    }

    private void explain() {
        System.out.println("\n--------------- COMPLEXITY NOTES ---------------");
        System.out.println("Linear Search : O(n)      checks elements one by one");
        System.out.println("Binary Search : O(log n)  halves the range each step (sorted data only)");
        System.out.println("BFS           : O(V + E)  explores level by level using a queue");
        System.out.println("DFS           : O(V + E)  goes deep first using a stack/recursion");
        System.out.println("BFS and DFS have the same complexity, so step counts are similar,");
        System.out.println("but the visiting ORDER is different.");
        System.out.println("Time (ns) can vary between runs because of JVM warm-up and the CPU;");
        System.out.println("step counts are the reliable measure.");
    }

    // ---------- Display All Results (main menu option 8) ----------
    public void displayAllResults() {
        System.out.println("\n=============================================");
        System.out.println("ALL RESULTS");
        System.out.println("=============================================");
        SearchOperations.SearchResult lin = searchOps.getLastLinear();
        SearchOperations.SearchResult bin = searchOps.getLastBinary();
        Graph.TraversalResult bfs = graph.getLastBfs();
        Graph.TraversalResult dfs = graph.getLastDfs();

        if (lin == null && bin == null && bfs == null && dfs == null && history.isEmpty()) {
            System.out.println("No results yet. Run searches, graph traversals or a performance comparison first.");
            return;
        }
        System.out.printf(ROW, "Operation", "Algorithm", "Steps", "Time(ns)", "Result");
        System.out.println(LINE);
        if (lin != null) printRow(false, "Search (last)", lin.algorithm, lin.steps, lin.timeNanos, searchText(lin.index));
        if (bin != null) printRow(false, "Search (last)", bin.algorithm, bin.steps, bin.timeNanos, searchText(bin.index));
        if (bfs != null) printRow(false, "Traversal (last)", "BFS", bfs.steps, bfs.timeNanos, "Order " + bfs.order);
        if (dfs != null) printRow(false, "Traversal (last)", "DFS", dfs.steps, dfs.timeNanos, "Order " + dfs.order);

        if (!history.isEmpty()) {
            System.out.println("\nComparison history (this session):");
            for (String h : history) System.out.println("  " + h);
        }
    }

    // ---------- Submenu ----------
    public void menu() {
        int choice;
        do {
            System.out.println("\n--------------- PERFORMANCE COMPARISON ---------------");
            System.out.println("1. Compare Linear vs Binary Search");
            System.out.println("2. Compare BFS vs DFS");
            System.out.println("3. Full Performance Table");
            System.out.println("4. Search Scaling Test (1000 / 10000 / 50000)");
            System.out.println("5. Complexity Notes");
            System.out.println("6. Return to Main Menu");
            choice = InputUtil.readIntInRange("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1: compareSearch(); break;
                case 2: compareGraph(); break;
                case 3: fullTable(); break;
                case 4: scalingTest(); break;
                case 5: explain(); break;
                default: break;
            }
        } while (choice != 6);
    }
}