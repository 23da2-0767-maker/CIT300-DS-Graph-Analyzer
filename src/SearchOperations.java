/** Linear and Binary search on the shared array, with step counting and timing. */
public class SearchOperations {

    /** Holds the outcome of one search so PerformanceAnalyzer can display it later. */
    public static class SearchResult {
        public final String algorithm;
        public final int key;
        public final int index;        // -1 if not found
        public final long steps;       // number of comparisons
        public final long timeNanos;
        public final int arraySize;

        public SearchResult(String algorithm, int key, int index, long steps, long timeNanos, int arraySize) {
            this.algorithm = algorithm;
            this.key = key;
            this.index = index;
            this.steps = steps;
            this.timeNanos = timeNanos;
            this.arraySize = arraySize;
        }
    }

    private final ArrayOperations arrayOps;
    private SearchResult lastLinear;
    private SearchResult lastBinary;

    public SearchOperations(ArrayOperations arrayOps) {
        this.arrayOps = arrayOps;
    }

    public SearchResult getLastLinear() { return lastLinear; }
    public SearchResult getLastBinary() { return lastBinary; }

    /** Linear Search: checks every element one by one. Time complexity O(n). */
    public SearchResult linearSearch(int key) {
        int[] a = arrayOps.getData();
        int n = arrayOps.getSize();
        long steps = 0;
        int foundIndex = -1;

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            steps++;
            if (a[i] == key) {
                foundIndex = i;
                break;
            }
        }
        long time = System.nanoTime() - start;

        lastLinear = new SearchResult("Linear Search", key, foundIndex, steps, time, n);
        return lastLinear;
    }

    /** Binary Search: halves the range each step. Needs a SORTED array. O(log n). */
    public SearchResult binarySearch(int key) {
        int[] a = arrayOps.getData();
        int n = arrayOps.getSize();
        long steps = 0;
        int foundIndex = -1;

        long start = System.nanoTime();
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            steps++;
            int mid = low + (high - low) / 2;
            if (a[mid] == key) {
                foundIndex = mid;
                break;
            } else if (a[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        long time = System.nanoTime() - start;

        lastBinary = new SearchResult("Binary Search", key, foundIndex, steps, time, n);
        return lastBinary;
    }

    /** Binary search only works on sorted data, so offer to sort first. */
    private boolean ensureSorted() {
        if (arrayOps.isSorted()) return true;
        System.out.println("Binary Search needs a sorted array, but the array is not sorted.");
        if (InputUtil.readYesNo("Sort the array now?")) {
            arrayOps.sort();
            System.out.println("Array sorted.");
            return true;
        }
        System.out.println("Binary Search cancelled.");
        return false;
    }

    private void printResult(SearchResult r) {
        System.out.println("Algorithm : " + r.algorithm);
        System.out.println("Key       : " + r.key);
        System.out.println("Result    : " + (r.index == -1 ? "Not found" : "Found at index " + r.index));
        System.out.println("Steps     : " + r.steps);
        System.out.println("Time      : " + r.timeNanos + " ns");
    }

    // ---------- Submenu ----------
    public void menu() {
        int choice;
        do {
            System.out.println("\n--------------- SEARCHING OPERATIONS ---------------");
            System.out.println("Current array size: " + arrayOps.getSize());
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search");
            System.out.println("3. Compare Linear vs Binary");
            System.out.println("4. Return to Main Menu");
            choice = InputUtil.readIntInRange("Enter your choice: ", 1, 4);

            switch (choice) {
                case 1:
                    if (arrayOps.isEmpty()) { System.out.println("Array is empty. Add data first."); break; }
                    printResult(linearSearch(InputUtil.readInt("Enter value to search: ")));
                    break;
                case 2:
                    if (arrayOps.isEmpty()) { System.out.println("Array is empty. Add data first."); break; }
                    if (!ensureSorted()) break;
                    printResult(binarySearch(InputUtil.readInt("Enter value to search: ")));
                    break;
                case 3:
                    if (arrayOps.isEmpty()) { System.out.println("Array is empty. Add data first."); break; }
                    if (!ensureSorted()) break;
                    int key = InputUtil.readInt("Enter value to search: ");
                    SearchResult lin = linearSearch(key);
                    SearchResult bin = binarySearch(key);
                    System.out.println("\nArray size: " + arrayOps.getSize() + "   Key: " + key);
                    System.out.printf("%-16s %-10s %-14s %s%n", "Algorithm", "Steps", "Time(ns)", "Result");
                    System.out.printf("%-16s %-10d %-14d %s%n", lin.algorithm, lin.steps, lin.timeNanos,
                            lin.index == -1 ? "Not found" : "Index " + lin.index);
                    System.out.printf("%-16s %-10d %-14d %s%n", bin.algorithm, bin.steps, bin.timeNanos,
                            bin.index == -1 ? "Not found" : "Index " + bin.index);
                    break;
                default:
                    break;
            }
        } while (choice != 4);
    }
}