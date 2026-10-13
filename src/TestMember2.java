import java.util.Scanner;

/** Quick test only - integrator will use these classes in the real Main menu */
public class TestMember2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyStack stack = new MyStack(5);
        MyQueue queue = new MyQueue(5);
        stack.showMenu(sc);
        queue.showMenu(sc);
    }
}