# CIT300 Data Structure & Graph Performance Analyzer

**Module:** CIT300 Data Structures and Algorithms
**Assignment:** Graded Practical Assignment 2 (Week 12)

## Project Description

A Java console-based application that demonstrates the practical use of data structures and algorithms: arrays, stacks, queues, linked lists, searching, graphs (BFS and DFS) and algorithmic performance comparison. Users work with each data structure through menus, with full input validation and handling of empty or invalid operations. A Performance Analyzer compares Linear vs Binary Search and BFS vs DFS using step counts and execution time.

## Team Members and Contributions

Student Name: AJ.Pasila 
Student ID: 23da2-0767
Assigned Responsibility: Array and Searching
Individual Contribution:
- Implemented `ArrayOperations` (insert at end/position, delete by index/value, search, display, sort, random data generation)
- Implemented `SearchOperations` (Linear Search and Binary Search with step counting and execution time)
- Implemented `InputUtil` for safe, validated console input
- Created the GitHub repository and base project structure
- Tested with empty, full and invalid inputs
- Integrated the array and searching modules with the main application

Student Name: KM.Pasmiya 
Student ID: 23da2-0766
Assigned Responsibility: Stack, Queue and Linked List
Individual Contribution:
- Implemented `MyStack` (push, pop, peek, display, empty/full handling)
- Implemented `MyQueue` (enqueue, dequeue, peek/front, display, empty/full handling)
- Implemented `Node`, `SinglyLinkedList` and `LinkedListMenu` (insert, delete, search, display)
- Tested all operations including empty-structure cases
- Integrated the modules with the main application

Student Name: AM.Rasviya 
Student ID: 23da2-0620
Assigned Responsibility: Graph Component
Individual Contribution:
- Implemented the `Graph` class using an adjacency list
- Implemented add vertex and add edge operations with validation
- Implemented graph display
- Implemented BFS traversal (queue) and DFS traversal (stack)
- Tested graph functionality, including empty and disconnected graphs
- Integrated the graph module with the main application

Student Name: N.Najiba 
Student ID: 23da2-0768
Assigned Responsibility: Main Menu, Performance Comparison, Integration and Testing
Individual Contribution:
- Implemented `Main` with the integrated main menu and submenu navigation
- Implemented `PerformanceAnalyzer` (Linear vs Binary Search, BFS vs DFS, scaling test, complexity notes, display of all results)
- Shared a single `Scanner` across all modules to avoid input conflicts
- Integrated all components and tested the complete system
- Wrote the project README

## Technologies Used

- Java (JDK 17 or later)
- Visual Studio Code
- Git and GitHub (branches, commits, pull requests)

## Main System Features

- **Array:** insert, delete, search, display, sort, random data generation
- **Stack:** push, pop, peek, display, with empty and full handling
- **Queue:** enqueue, dequeue, peek/front, display, with empty and full handling
- **Linked List:** insert, delete, search, display
- **Searching:** Linear Search and Binary Search with step count and execution time
- **Graph:** add vertex, add edge, display, search, BFS and DFS traversal
- **Performance Comparison:** steps and execution time for Linear vs Binary Search and BFS vs DFS, plus a scaling test
- **Display All Results:** shows the latest search and traversal results
- Input validation and handling of empty data structures throughout

## Main Menu

```
=============================================
DATA STRUCTURE & GRAPH ANALYZER
=============================================
1. Array Operations
2. Stack Operations
3. Queue Operations
4. Linked List Operations
5. Searching Operations
6. Graph Operations
7. Performance Comparison
8. Display All Results
9. Exit
```

## Algorithmic Complexity

| Operation | Algorithm | Time Complexity |
|---|---|---|
| Search | Linear Search | O(n) |
| Search | Binary Search (sorted data) | O(log n) |
| Graph traversal | BFS | O(V + E) |
| Graph traversal | DFS | O(V + E) |

Linear Search checks elements one by one, while Binary Search halves the search range each step, so its step count grows very slowly. BFS and DFS have the same complexity, so their step counts are similar, but the order in which they visit vertices is different. Execution time can vary between runs because of JVM warm-up; step counts are the reliable measure.

## How to Run

1. Install JDK 17 or later and check with `java -version` and `javac -version`.
2. Clone the repository:
   ```
   git clone https://github.com/23da2-0767-maker/CIT300-DS-Graph-Analyzer.git
   cd CIT300-DS-Graph-Analyzer
   ```
3. Compile:
   
   javac -d out src/*.java
   
4. Run:
   
   java -cp out Main
   

Alternatively, open the folder in VS Code with the Extension Pack for Java and run `Main.java`.

## Project Structure

```
src/
 ├── Main.java
 ├── InputUtil.java
 ├── ArrayOperations.java
 ├── SearchOperations.java
 ├── MyStack.java
 ├── MyQueue.java
 ├── Node.java
 ├── SinglyLinkedList.java
 ├── LinkedListMenu.java
 ├── Graph.java
 └── PerformanceAnalyzer.java
```

## Git Note

The first array and searching commit was made from a laptop with a different Git configuration, and was later recreated with the correct author. The pull request history shows the original work.
