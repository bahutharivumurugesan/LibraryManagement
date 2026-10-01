package library;

import java.time.LocalDate;
import java.util.*;

/** Business rules. Data is kept in memory; swap the lists for DAO classes (JDBC/MySQL) later. */
public class LibraryService {
    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Transaction> txns = new ArrayList<>();
    private int nextBookId = 1, nextTxnId = 1;

    public LibraryService() {
        addBook("Clean Code", "Robert C. Martin", 3);
        addBook("Effective Java", "Joshua Bloch", 2);
        addBook("Head First Java", "Kathy Sierra", 2);
        addBook("Operating System Concepts", "Silberschatz", 1);
        addBook("Database System Concepts", "Korth", 3);
        members.add(new Member(1, "Ananya R"));
        members.add(new Member(2, "Karthik S"));
        members.add(new Member(3, "Meera P"));
    }

    public Book addBook(String title, String author, int copies) {
        Book b = new Book(nextBookId++, title, author, copies);
        books.add(b);
        return b;
    }

    public List<Book> search(String q) {
        String s = q.trim().toLowerCase();
        List<Book> out = new ArrayList<>();
        for (Book b : books)
            if (s.isEmpty() || (b.getTitle() + " " + b.getAuthor()).toLowerCase().contains(s)) out.add(b);
        return out;
    }

    public List<Member> getMembers() { return members; }
    public List<Transaction> getTransactions() { return txns; }

    public Transaction issueBook(int memberId, int bookId) {
        Book b = findBook(bookId);
        if (!b.isAvailable()) throw new IllegalStateException("No copies left for " + b.getTitle());
        LocalDate today = LocalDate.now();
        Transaction t = new Transaction(nextTxnId++, memberId, bookId, today, today.plusDays(FineCalculator.LOAN_DAYS));
        txns.add(t);
        b.setCopies(b.getCopies() - 1);
        return t;
    }

    /** daysKept lets you simulate late returns when demoing. */
    public int returnBook(int memberId, int bookId, int daysKept) {
        for (Transaction t : txns) {
            if (t.isOpen() && t.getMemberId() == memberId && t.getBookId() == bookId) {
                LocalDate returned = t.getIssueDate().plusDays(daysKept);
                int fine = FineCalculator.calculate(t.getIssueDate(), returned);
                t.close(returned, fine);
                Book b = findBook(bookId);
                b.setCopies(b.getCopies() + 1);
                return fine;
            }
        }
        throw new IllegalStateException("This member has not borrowed that book");
    }

    public int issuedCount(int bookId) {
        int n = 0;
        for (Transaction t : txns) if (t.isOpen() && t.getBookId() == bookId) n++;
        return n;
    }

    public int totalFines() {
        int sum = 0;
        for (Transaction t : txns) sum += t.getFine();
        return sum;
    }

    private Book findBook(int id) {
        for (Book b : books) if (b.getId() == id) return b;
        throw new NoSuchElementException("Book not found: " + id);
    }
}
