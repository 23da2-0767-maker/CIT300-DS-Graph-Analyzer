public class SinglyLinkedList {
    private Node head;
    private int size;
    private int lastSearchSteps; // Performance comparison-ku

    public boolean isEmpty() { return head == null; }
    public int getSize() { return size; }
    public int getLastSearchSteps() { return lastSearchSteps; }

    // Insert at beginning - O(1)
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Insert at end - O(n)
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        size++;
    }

    // Insert at position (1-based) - O(n)
    public boolean insertAtPosition(int position, int value) {
        if (position < 1 || position > size + 1) return false;
        if (position == 1) { insertAtBeginning(value); return true; }
        Node current = head;
        for (int i = 1; i < position - 1; i++) current = current.next;
        Node newNode = new Node(value);
        newNode.next = current.next;
        current.next = newNode;
        size++;
        return true;
    }

    // Delete first occurrence of value - O(n)
    public boolean deleteByValue(int value) {
        if (isEmpty()) return false;
        if (head.data == value) {
            head = head.next;
            size--;
            return true;
        }
        Node current = head;
        while (current.next != null && current.next.data != value) {
            current = current.next;
        }
        if (current.next == null) return false; // not found
        current.next = current.next.next;
        size--;
        return true;
    }

    // Search - returns position (1-based) or -1, counts steps - O(n)
    public int search(int value) {
        lastSearchSteps = 0;
        Node current = head;
        int position = 1;
        while (current != null) {
            lastSearchSteps++;
            if (current.data == value) return position;
            current = current.next;
            position++;
        }
        return -1;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Linked list is empty.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}