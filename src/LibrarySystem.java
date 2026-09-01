import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

class Loan {
    private final String memberId;
    private final String bookId;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(String memberId, String bookId, LocalDate issueDate, LocalDate dueDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getMemberId() { return memberId; }
    public String getBookId() { return bookId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public long overdueDays(LocalDate date) {
        if (!date.isAfter(dueDate)) return 0;
        return ChronoUnit.DAYS.between(dueDate, date);
    }
}

class LibrarySystem {
    private final Inventory inventory = new Inventory();
    private final List<Member> members = new ArrayList<>();
    private final Set<Member> memberSet = new HashSet<>();
    private final Map<String, Member> memberMap = new HashMap<>();
    private final Hashtable<String, String> notificationLog = new Hashtable<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();
    private int reservationSequence = 1001;

    public Inventory getInventory() { return inventory; }
    public List<Member> getMembers() { return new ArrayList<>(members); }
    public List<Loan> getLoans() { return new ArrayList<>(loans); }

    public synchronized void registerMember(Member member) throws InvalidInputException {
        if (member.getMemberId().isBlank() || member.getName().isBlank())
            throw new InvalidInputException("Member ID and name are required.");
        if (memberMap.containsKey(member.getMemberId()))
            throw new InvalidInputException("Member ID already exists.");
        members.add(member);
        memberSet.add(member);
        memberMap.put(member.getMemberId(), member);
    }

    public synchronized Member findMember(String memberId) throws InvalidMemberException {
        Member member = memberMap.get(memberId);
        if (member == null) throw new InvalidMemberException("Invalid member ID: " + memberId);
        return member;
    }

    public synchronized Book searchBook(String keyword) {
        for (Book book : inventory.getAllBooks()) {
            String text = (book.getBookId() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getCategory()).toLowerCase();
            if (text.contains(keyword.toLowerCase())) return book;
        }
        return null;
    }

    public synchronized List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        Iterator<Book> it = inventory.getAllBooks().iterator();
        while (it.hasNext()) {
            Book book = it.next();
            String text = (book.getBookId() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getCategory()).toLowerCase();
            if (text.contains(keyword.toLowerCase())) result.add(book);
        }
        return result;
    }

    public synchronized void updateBook(String bookId, String title, String author, String category) throws InvalidInputException {
        Book book = inventory.getBook(bookId);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
    }

    public synchronized Loan issueBook(String memberId, String bookId) throws Exception {
        Member member = findMember(memberId);
        if (member.hasBorrowed(bookId)) throw new InvalidInputException("Member already borrowed this book.");
        if (member.getBorrowedBookIds().size() >= member.getBorrowLimit())
            throw new InvalidInputException("Borrowing limit reached for " + member.getMembershipType() + " member.");
        inventory.issueBook(bookId);
        LocalDate issueDate = LocalDate.now();
        Loan loan = new Loan(memberId, bookId, issueDate, issueDate.plusDays(14));
        loans.add(loan);
        member.addBorrowedBook(bookId);
        cancelReservationForMember(memberId, bookId);
        return loan;
    }

    public synchronized double returnBook(String memberId, String bookId, LocalDate returnDate) throws Exception {
        Member member = findMember(memberId);
        Loan activeLoan = null;
        for (Loan loan : loans) {
            if (loan.getMemberId().equals(memberId) && loan.getBookId().equals(bookId) && loan.getReturnDate() == null) {
                activeLoan = loan;
                break;
            }
        }
        if (activeLoan == null) throw new InvalidInputException("No active loan found.");
        activeLoan.setReturnDate(returnDate);
        member.removeBorrowedBook(bookId);
        inventory.returnBook(bookId);
        return activeLoan.overdueDays(returnDate) * member.getFinePerDay();
    }

    public synchronized void reserveBook(String memberId, String bookId) throws Exception {
        findMember(memberId);
        Book book = inventory.getBook(bookId);
        if (book.getAvailableCopies() > 0) throw new BookUnavailableException("Book is available; reservation is not required.");
        for (Reservation r : reservations) {
            if (r.getMemberId().equals(memberId) && r.getBookId().equals(bookId))
                throw new DuplicateReservationException("Duplicate reservation is not allowed.");
        }
        reservations.add(new Reservation("R" + reservationSequence++, memberId, bookId));
    }

    public synchronized void cancelReservation(String memberId, String bookId) throws InvalidInputException {
        if (!cancelReservationForMember(memberId, bookId)) throw new InvalidInputException("Reservation not found.");
    }

    private boolean cancelReservationForMember(String memberId, String bookId) {
        ListIterator<Reservation> it = reservations.listIterator();
        while (it.hasNext()) {
            Reservation r = it.next();
            if (r.getMemberId().equals(memberId) && r.getBookId().equals(bookId)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public synchronized List<Reservation> getReservationsForBook(String bookId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) if (r.getBookId().equals(bookId)) result.add(r);
        return result;
    }

    public synchronized void sendNotifications(LocalDate today) {
        for (Loan loan : loans) {
            if (loan.getReturnDate() != null) continue;
            long daysToDue = ChronoUnit.DAYS.between(today, loan.getDueDate());
            Notification n;
            if (daysToDue == 3) {
                n = new DueDateNotification(loan.getMemberId(), "Book " + loan.getBookId() + " is due in 3 days (" + loan.getDueDate() + ").");
            } else if (daysToDue < 0) {
                n = new OverdueNotification(loan.getMemberId(), "Book " + loan.getBookId() + " is overdue by " + (-daysToDue) + " day(s).");
            } else continue;
            n.send();
            notificationLog.put(loan.getMemberId(), n.getClass().getSimpleName() + " sent for " + loan.getBookId());
        }
    }

    public synchronized void printMembers() {
        System.out.println("\n--- MEMBERS (HashSet + ArrayList) ---");
        for (Member member : memberSet) System.out.println(member);
    }

    public synchronized void printLoans() {
        System.out.println("\n--- CIRCULATION REPORT ---");
        if (loans.isEmpty()) { System.out.println("No circulation records."); return; }
        for (Loan loan : loans) {
            String status = loan.getReturnDate() == null ? "ACTIVE" : "RETURNED on " + loan.getReturnDate();
            System.out.printf("Member=%s | Book=%s | Issued=%s | Due=%s | %s%n",
                    loan.getMemberId(), loan.getBookId(), loan.getIssueDate(), loan.getDueDate(), status);
        }
    }

    public synchronized void printReservationQueue(String bookId) {
        System.out.println("\n--- RESERVATION QUEUE: " + bookId + " ---");
        List<Reservation> queue = getReservationsForBook(bookId);
        if (queue.isEmpty()) System.out.println("No reservations.");
        else for (int i = 0; i < queue.size(); i++) System.out.println((i + 1) + ". " + queue.get(i));
    }

    public synchronized void printUtilizationReport() {
        System.out.println("\n--- INVENTORY UTILIZATION ---");
        int total = 0, available = 0;
        for (String id : List.of("B101", "B102", "B103", "B104", "B105")) {
            try {
                Book b = inventory.getBook(id);
                total += b.getTotalCopies();
                available += b.getAvailableCopies();
                double utilization = b.getTotalCopies() == 0 ? 0 : (100.0 * (b.getTotalCopies() - b.getAvailableCopies()) / b.getTotalCopies());
                System.out.printf("%-6s %-25s Utilization: %6.2f%%%n", b.getBookId(), b.getTitle(), utilization);
            } catch (Exception ignored) { }
        }
        if (total > 0) System.out.printf("Overall utilization: %.2f%% (%d of %d copies in circulation)%n", 100.0 * (total - available) / total, total - available, total);
    }

    public synchronized Loan findActiveLoanForBook(String bookId) {
        for (Loan loan : loans) {
            if (loan.getBookId().equals(bookId) && loan.getReturnDate() == null) return loan;
        }
        return null;
    }

    public synchronized String getNotificationLog(String memberId) {
        return notificationLog.get(memberId);
    }
}
