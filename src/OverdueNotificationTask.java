import java.time.LocalDate;

class OverdueNotificationTask extends Thread {
    private final LibrarySystem library;
    private final LocalDate date;

    public OverdueNotificationTask(LibrarySystem library, LocalDate date) {
        super("NotificationTask");
        this.library = library;
        this.date = date;
        setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        System.out.println(getName() + " started [priority=" + getPriority() + "]");
        library.sendNotifications(date);
        System.out.println(getName() + " completed");
    }
}
