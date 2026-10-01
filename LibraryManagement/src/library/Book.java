package library;

public class Book {
    private final int id;
    private final String title, author;
    private int copies;

    public Book(int id, String title, String author, int copies) {
        this.id = id; this.title = title; this.author = author; this.copies = copies;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }
    public boolean isAvailable() { return copies > 0; }
}
