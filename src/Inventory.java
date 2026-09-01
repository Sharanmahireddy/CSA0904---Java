import java.util.HashMap;
import java.util.Map;

class Inventory {
    private final Map<String, Book> books = new HashMap<>();

    public synchronized void addBook(Book book) throws InvalidInputException {
        if (books.containsKey(book.getBookId()))
            throw new InvalidInputException("Book ID already exists: " + book.getBookId());
        books.put(book.getBookId(), book);
    }

    public synchronized Book getBook(String bookId) throws InvalidInputException {
        Book book = books.get(bookId);
        if (book == null) throw new InvalidInputException("Book not found: " + bookId);
        return book;
    }

    public synchronized boolean issueBook(String bookId) throws InvalidInputException, BookUnavailableException {
        Book book = getBook(bookId);
        if (!book.issueCopy()) throw new BookUnavailableException("No available copy for: " + book.getTitle());
        return true;
    }

    public synchronized void returnBook(String bookId) throws InvalidInputException {
        getBook(bookId).returnCopy();
        notifyAll();
    }

    public synchronized void waitForAvailability(String bookId) throws InterruptedException, InvalidInputException {
        while (getBook(bookId).getAvailableCopies() == 0) wait(1000);
    }

    public synchronized void printInventory() {
        System.out.println("\n--- INVENTORY ---");
        for (Book book : books.values()) System.out.println(book);
    }

    public synchronized java.util.List<Book> getAllBooks() { return new java.util.ArrayList<>(books.values()); }

    public synchronized int size() { return books.size(); }
}
