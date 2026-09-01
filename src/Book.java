class Book {
    private final String bookId;
    private String title;
    private String author;
    private String category;
    private final int totalCopies;
    private int availableCopies;

    public Book(String bookId, String title, String author, String category, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }

    public synchronized boolean issueCopy() {
        if (availableCopies <= 0) return false;
        availableCopies--;
        return true;
    }

    public synchronized void returnCopy() {
        if (availableCopies < totalCopies) availableCopies++;
    }

    @Override
    public String toString() {
        return String.format("%s | %-24s | %-18s | %-12s | %d/%d available",
                bookId, title, author, category, availableCopies, totalCopies);
    }
}
