class ConcurrentIssueTask extends Thread {
    private final LibrarySystem library;
    private final String memberId;
    private final String bookId;

    public ConcurrentIssueTask(LibrarySystem library, String memberId, String bookId, int priority) {
        super("IssueTask-" + memberId);
        this.library = library;
        this.memberId = memberId;
        this.bookId = bookId;
        setPriority(priority);
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " [priority=" + getPriority() + "] requesting " + bookId);
            Loan loan = library.issueBook(memberId, bookId);
            System.out.println(getName() + " SUCCESS: due date = " + loan.getDueDate());
        } catch (Exception e) {
            System.out.println(getName() + " REJECTED: " + e.getMessage());
        }
    }
}
