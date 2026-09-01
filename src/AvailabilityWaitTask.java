class AvailabilityWaitTask extends Thread {
    private final LibrarySystem library;
    private final String bookId;

    public AvailabilityWaitTask(LibrarySystem library, String bookId) {
        super("ReservationWaitTask");
        this.library = library;
        this.bookId = bookId;
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " waiting for " + bookId + " using wait()/notifyAll().");
            library.getInventory().waitForAvailability(bookId);
            System.out.println(getName() + " resumed: " + bookId + " is available.");
        } catch (Exception e) {
            System.out.println(getName() + " stopped: " + e.getMessage());
        }
    }
}
