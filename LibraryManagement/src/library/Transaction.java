package library;

import java.time.LocalDate;

public class Transaction {
    private final int id, memberId, bookId;
    private final LocalDate issueDate, dueDate;
    private LocalDate returnDate;
    private int fine;

    public Transaction(int id, int memberId, int bookId, LocalDate issueDate, LocalDate dueDate) {
        this.id = id; this.memberId = memberId; this.bookId = bookId;
        this.issueDate = issueDate; this.dueDate = dueDate;
    }
    public int getId() { return id; }
    public int getMemberId() { return memberId; }
    public int getBookId() { return bookId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public int getFine() { return fine; }
    public boolean isOpen() { return returnDate == null; }
    public void close(LocalDate returnDate, int fine) { this.returnDate = returnDate; this.fine = fine; }
}
