import java.util.Arrays;
import java.util.Random;

/** Fixed-size integer array with insert, delete, search and display operations. */
public class ArrayOperations {
    public static final int MAX_SIZE = 50000;

    private final int[] data = new int[MAX_SIZE];
    private int size = 0;

    // ---------- Basic info (used by SearchOperations) ----------
    public int[] getData()   { return data; }
    public int getSize()     { return size; }
    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == MAX_SIZE; }

    public boolean isSorted() {
        for (int i = 1; i < size; i++) {
            if (data[i - 1] > data[i]) return false;
        }
        return true;
    }

    /** Sorts the used part of the array in ascending order (needed for Binary Search). */
    public void sort() {
        Arrays.sort(data, 0, size);
    }

    // ---------- Insert: O(1) at end, O(n) at a position (shifting) ----------
    public boolean insertAtEnd(int value) {
        return insertAt(size, value);
    }

    public boolean insertAt(int index, int value) {
        if (isFull()) {
            System.out.println("Array is full. Cannot insert.");
            return false;
        }
        if (index < 0 || index > size) {
            System.out.println("Invalid position. Valid range: 0 to " + size);
            return false;
        }
        for (int i = size; i > index; i--) {   // shift elements right
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
        return true;
    }

    // ---------- Delete: O(n) because elements are shifted left ----------
    public boolean deleteAt(int index) {
        if (isEmpty()) {
            System.out.println("Array is empty. Nothing to delete.");
            return false;
        }
        if (index < 0 || index >= size) {
            System.out.println("Invalid index. Valid range: 0 to " + (size - 1));
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return true;
    }

    public boolean deleteValue(int value) {
        int index = search(value);
        if (index == -1) {
            System.out.println("Value " + value + " not found.");
            return false;
        }
        return deleteAt(index);
    }

    // ---------- Search: simple linear search, O(n) ----------
    public int search(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) return i;
        }
        return -1;
    }

    // ---------- Display ----------
    public void display() {
        if (isEmpty()) {
            System.out.println("Array is empty.");
            return;
        }
        int limit = Math.min(size, 50);   // avoid printing thousands of numbers
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < limit; i++) {
            sb.append(data[i]);
            if (i < limit - 1) sb.append(", ");
        }
        if (size > limit) sb.append(", ...");
        sb.append("]");
        System.out.println(sb);
        System.out.println("Total elements: " + size);
    }

    // ---------- Extra helpers ----------
    /** Replaces the array content with n random numbers (useful for performance tests). */
    public void generateRandom(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(100000) + 1;
        }
        size = n;
    }

    public void clear() {
        size = 0;
    }

    // ---------- Submenu ----------
    public void menu() {
        int choice;
        do {
            System.out.println("\n--------------- ARRAY OPERATIONS ---------------");
            System.out.println("1. Insert at End");
            System.out.println("2. Insert at Position");
            System.out.println("3. Delete by Index");
            System.out.println("4. Delete by Value");
            System.out.println("5. Search for a Value");
            System.out.println("6. Display Array");
            System.out.println("7. Sort Array");
            System.out.println("8. Generate Random Data");
            System.out.println("9. Clear Array");
            System.out.println("10. Return to Main Menu");
            choice = InputUtil.readIntInRange("Enter your choice: ", 1, 10);

            switch (choice) {
                case 1:
                    int v1 = InputUtil.readInt("Enter value: ");
                    if (insertAtEnd(v1)) System.out.println("Inserted " + v1);
                    break;
                case 2:
                    int pos = InputUtil.readInt("Enter position (0 to " + size + "): ");
                    int v2 = InputUtil.readInt("Enter value: ");
                    if (insertAt(pos, v2)) System.out.println("Inserted " + v2 + " at position " + pos);
                    break;
                case 3:
                    if (isEmpty()) { System.out.println("Array is empty."); break; }
                    int idx = InputUtil.readInt("Enter index to delete (0 to " + (size - 1) + "): ");
                    if (deleteAt(idx)) System.out.println("Deleted element at index " + idx);
                    break;
                case 4:
                    if (isEmpty()) { System.out.println("Array is empty."); break; }
                    int v3 = InputUtil.readInt("Enter value to delete: ");
                    if (deleteValue(v3)) System.out.println("Deleted " + v3);
                    break;
                case 5:
                    if (isEmpty()) { System.out.println("Array is empty."); break; }
                    int key = InputUtil.readInt("Enter value to search: ");
                    int found = search(key);
                    System.out.println(found == -1 ? "Not found." : "Found at index " + found);
                    break;
                case 6:
                    display();
                    break;
                case 7:
                    if (isEmpty()) { System.out.println("Array is empty."); break; }
                    sort();
                    System.out.println("Array sorted in ascending order.");
                    break;
                case 8:
                    int n = InputUtil.readIntInRange("How many numbers (1 to " + MAX_SIZE + "): ", 1, MAX_SIZE);
                    generateRandom(n);
                    System.out.println(n + " random numbers generated.");
                    break;
                case 9:
                    clear();
                    System.out.println("Array cleared.");
                    break;
                default:
                    break;
            }
        } while (choice != 10);
    }
}