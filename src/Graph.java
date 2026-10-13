import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/** Undirected graph stored as an adjacency list, with BFS and DFS traversal. */
public class Graph {

    /** Result of one traversal, used by PerformanceAnalyzer. */
    public static class TraversalResult {
        public final String algorithm;
        public final int startVertex;
        public final List<Integer> order;
        public final long steps;        // vertices visited + edges examined
        public final long timeNanos;

        public TraversalResult(String algorithm, int startVertex, List<Integer> order,
                               long steps, long timeNanos) {
            this.algorithm = algorithm;
            this.startVertex = startVertex;
            this.order = order;
            this.steps = steps;
            this.timeNanos = timeNanos;
        }
    }

    // TreeMap keeps vertices sorted so output is predictable
    private final Map<Integer, List<Integer>> adjacency = new TreeMap<>();
    private TraversalResult lastBfs;
    private TraversalResult lastDfs;

    // ---------- Basic info ----------
    public boolean hasVertex(int v)        { return adjacency.containsKey(v); }
    public int getVertexCount()            { return adjacency.size(); }
    public List<Integer> getVertices()     { return new ArrayList<>(adjacency.keySet()); }
    public TraversalResult getLastBfs()    { return lastBfs; }
    public TraversalResult getLastDfs()    { return lastDfs; }

    public int getEdgeCount() {
        int total = 0;
        for (List<Integer> neighbours : adjacency.values()) total += neighbours.size();
        return total / 2;   // each undirected edge is stored twice
    }

    // ---------- Add vertex / edge ----------
    public boolean addVertex(int v) {
        if (hasVertex(v)) {
            System.out.println("Vertex " + v + " already exists.");
            return false;
        }
        adjacency.put(v, new ArrayList<>());
        return true;
    }

    public boolean addEdge(int a, int b) {
        if (!hasVertex(a) || !hasVertex(b)) {
            System.out.println("Both vertices must exist first.");
            return false;
        }
        if (a == b) {
            System.out.println("Self-loops are not allowed.");
            return false;
        }
        if (adjacency.get(a).contains(b)) {
            System.out.println("Edge " + a + " - " + b + " already exists.");
            return false;
        }
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
        Collections.sort(adjacency.get(a));
        Collections.sort(adjacency.get(b));
        return true;
    }

    // ---------- Display ----------
    public void display() {
        if (adjacency.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }
        System.out.println("Adjacency list (" + getVertexCount() + " vertices, " + getEdgeCount() + " edges):");
        for (Map.Entry<Integer, List<Integer>> e : adjacency.entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }
    }

    // ---------- BFS: queue, level by level. O(V + E) ----------
    public TraversalResult bfs(int start) {
        if (!hasVertex(start)) return null;

        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        long steps = 0;

        long t0 = System.nanoTime();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);
            steps++;                                   // vertex visited
            for (int next : adjacency.get(current)) {
                steps++;                               // edge examined
                if (visited.add(next)) queue.add(next);
            }
        }
        long time = System.nanoTime() - t0;

        lastBfs = new TraversalResult("BFS", start, order, steps, time);
        return lastBfs;
    }

    // ---------- DFS: stack, goes deep first. O(V + E) ----------
    public TraversalResult dfs(int start) {
        if (!hasVertex(start)) return null;

        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        long steps = 0;

        long t0 = System.nanoTime();
        stack.push(start);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited.add(current)) continue;       // already visited
            order.add(current);
            steps++;                                   // vertex visited
            List<Integer> neighbours = adjacency.get(current);
            // push in reverse so the smallest neighbour is explored first
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                steps++;                               // edge examined
                int next = neighbours.get(i);
                if (!visited.contains(next)) stack.push(next);
            }
        }
        long time = System.nanoTime() - t0;

        lastDfs = new TraversalResult("DFS", start, order, steps, time);
        return lastDfs;
    }

    // ---------- Helpers for the menu ----------
    private void loadSample() {
        adjacency.clear();
        for (int v = 1; v <= 6; v++) addVertex(v);
        int[][] edges = {{1, 2}, {1, 3}, {2, 4}, {2, 5}, {3, 6}, {5, 6}};
        for (int[] e : edges) addEdge(e[0], e[1]);
        System.out.println("Sample graph loaded (6 vertices, 6 edges).");
    }

    private void printTraversal(TraversalResult r) {
        System.out.println("Algorithm : " + r.algorithm);
        System.out.println("Start     : " + r.startVertex);
        System.out.println("Order     : " + r.order);
        System.out.println("Visited   : " + r.order.size() + " of " + getVertexCount() + " vertices");
        if (r.order.size() < getVertexCount()) {
            System.out.println("Note      : graph is disconnected; only reachable vertices are visited.");
        }
        System.out.println("Steps     : " + r.steps);
        System.out.println("Time      : " + r.timeNanos + " ns");
    }

    /** Asks for an existing start vertex, or returns -1 if the graph is empty. */
    private int askVertex(String prompt) {
        if (adjacency.isEmpty()) {
            System.out.println("Graph is empty. Add vertices first.");
            return -1;
        }
        System.out.println("Vertices: " + getVertices());
        while (true) {
            int v = InputUtil.readInt(prompt);
            if (hasVertex(v)) return v;
            System.out.println("Vertex " + v + " does not exist. Try again.");
        }
    }

    // ---------- Submenu ----------
    public void menu() {
        int choice;
        do {
            System.out.println("\n--------------- GRAPH OPERATIONS ---------------");
            System.out.println("1. Add Vertex");
            System.out.println("2. Add Edge");
            System.out.println("3. Display Graph");
            System.out.println("4. BFS Traversal");
            System.out.println("5. DFS Traversal");
            System.out.println("6. Search for a Vertex");
            System.out.println("7. Load Sample Graph");
            System.out.println("8. Clear Graph");
            System.out.println("9. Return to Main Menu");
            choice = InputUtil.readIntInRange("Enter your choice: ", 1, 9);

            switch (choice) {
                case 1:
                    int v = InputUtil.readInt("Enter vertex number: ");
                    if (addVertex(v)) System.out.println("Vertex " + v + " added.");
                    break;
                case 2:
                    if (getVertexCount() < 2) { System.out.println("Add at least 2 vertices first."); break; }
                    int a = InputUtil.readInt("Enter first vertex: ");
                    int b = InputUtil.readInt("Enter second vertex: ");
                    if (addEdge(a, b)) System.out.println("Edge " + a + " - " + b + " added.");
                    break;
                case 3:
                    display();
                    break;
                case 4:
                    int s1 = askVertex("Enter start vertex: ");
                    if (s1 != -1) printTraversal(bfs(s1));
                    break;
                case 5:
                    int s2 = askVertex("Enter start vertex: ");
                    if (s2 != -1) printTraversal(dfs(s2));
                    break;
                case 6:
                    if (adjacency.isEmpty()) { System.out.println("Graph is empty."); break; }
                    int key = InputUtil.readInt("Enter vertex to search: ");
                    if (hasVertex(key)) {
                        System.out.println("Vertex " + key + " found. Neighbours: " + adjacency.get(key));
                    } else {
                        System.out.println("Vertex " + key + " not found.");
                    }
                    break;
                case 7:
                    loadSample();
                    break;
                case 8:
                    adjacency.clear();
                    lastBfs = null;
                    lastDfs = null;
                    System.out.println("Graph cleared.");
                    break;
                default:
                    break;
            }
        } while (choice != 9);
    }
}