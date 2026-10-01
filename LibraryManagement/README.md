# Library Management System (Java Swing)

A Java-Powered Solution for Smart & Efficient Library Management.

## Run in VS Code
1. Install the **Extension Pack for Java** (Microsoft) and JDK 17+.
2. File > Open Folder > select this `LibraryManagement` folder.
3. Open `src/library/Main.java` and click **Run** above `main`.

## Run from terminal
    javac -d out src/library/*.java
    java -cp out library.Main

## Features
Search, add books, issue and return, automatic fine (Rs 2/day after 14 days), total fines.

## Structure
Book, Member, Transaction (models) | FineCalculator, LibraryService (business rules) | Main (Swing UI)

## Next step
Data is in memory. Add BookDAO / MemberDAO / TransactionDAO with JDBC + MySQL to persist it.
