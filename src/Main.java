import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibrarySystem library = new LibrarySystem();

    public static void main(String[] args) {
        loadSampleData();
        System.out.println("===============================================");
        System.out.println(" SMART LIBRARY MANAGEMENT SYSTEM - JAVA");
        System.out.println("===============================================");
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> registerMember();
                    case 2 -> addBook();
                    case 3 -> library.getInventory().printInventory();
                    case 4 -> searchBooks();
                    case 5 -> updateBook();
                    case 6 -> issueBook();
                    case 7 -> returnBook();
                    case 8 -> reserveBook();
                    case 9 -> cancelReservation();
                    case 10 -> library.printMembers();
                    case 11 -> library.printLoans();
                    case 12 -> sendNotifications();
                    case 13 -> concurrentDemo();
                    case 14 -> utilizationReport();
                    case 15 -> reservationQueue();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid menu choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: enter a number.");
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using Smart Library Management System.");
    }

    private static void printMenu() {
        System.out.println("\n--------------- MENU ----------------");
        System.out.println("1. Register Member");
        System.out.println("2. Add Book");
        System.out.println("3. Display Inventory");
        System.out.println("4. Search Books");
        System.out.println("5. Update Book");
        System.out.println("6. Issue Book");
        System.out.println("7. Return Book / Calculate Fine");
        System.out.println("8. Reserve Unavailable Book");
        System.out.println("9. Cancel Reservation");
        System.out.println("10. Display Members");
        System.out.println("11. Circulation Report");
        System.out.println("12. Due / Overdue Notifications");
        System.out.println("13. Multithreading Issue Demo");
        System.out.println("14. Inventory Utilization Report");
        System.out.println("15. Reservation Queue");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void registerMember() throws Exception {
        System.out.print("Member ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Type (1-Student, 2-Faculty): "); int type = Integer.parseInt(scanner.nextLine());
        Member member = type == 1 ? new StudentMember(id, name, email) : new FacultyMember(id, name, email);
        library.registerMember(member);
        System.out.println("Member registered: " + member.getMemberId() + " (" + member.getMembershipType() + ")");
    }

    private static void addBook() throws Exception {
        System.out.print("Book ID: "); String id = scanner.nextLine();
        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author: "); String author = scanner.nextLine();
        System.out.print("Category: "); String category = scanner.nextLine();
        System.out.print("Total copies: "); int copies = Integer.parseInt(scanner.nextLine());
        if (copies <= 0) throw new InvalidInputException("Copies must be positive.");
        library.getInventory().addBook(new Book(id, title, author, category, copies));
        System.out.println("Book added successfully.");
    }

    private static void searchBooks() {
        System.out.print("Enter title/author/category/ID keyword: ");
        String keyword = scanner.nextLine();
        List<Book> books = library.searchBooks(keyword);
        if (books.isEmpty()) System.out.println("No matching books found.");
        else { System.out.println("\nSearch results:"); for (Book book : books) System.out.println(book); }
    }

    private static void updateBook() throws Exception {
        System.out.print("Book ID: "); String id = scanner.nextLine();
        System.out.print("New title: "); String title = scanner.nextLine();
        System.out.print("New author: "); String author = scanner.nextLine();
        System.out.print("New category: "); String category = scanner.nextLine();
        library.updateBook(id, title, author, category);
        System.out.println("Book record updated.");
    }

    private static void issueBook() throws Exception {
        System.out.print("Member ID: "); String memberId = scanner.nextLine();
        System.out.print("Book ID: "); String bookId = scanner.nextLine();
        Loan loan = library.issueBook(memberId, bookId);
        System.out.println("Book issued successfully. Due date: " + loan.getDueDate());
    }

    private static void returnBook() throws Exception {
        System.out.print("Member ID: "); String memberId = scanner.nextLine();
        System.out.print("Book ID: "); String bookId = scanner.nextLine();
        System.out.print("Return date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        double fine = library.returnBook(memberId, bookId, date);
        System.out.printf("Book returned successfully. Fine = Rs. %.2f%n", fine);
    }

    private static void reserveBook() throws Exception {
        System.out.print("Member ID: "); String memberId = scanner.nextLine();
        System.out.print("Book ID: "); String bookId = scanner.nextLine();
        library.reserveBook(memberId, bookId);
        System.out.println("Reservation added to the waitlist.");
    }

    private static void cancelReservation() throws Exception {
        System.out.print("Member ID: "); String memberId = scanner.nextLine();
        System.out.print("Book ID: "); String bookId = scanner.nextLine();
        library.cancelReservation(memberId, bookId);
        System.out.println("Reservation cancelled.");
    }

    private static void sendNotifications() throws InterruptedException {
        OverdueNotificationTask task = new OverdueNotificationTask(library, LocalDate.now().plusDays(11));
        task.start();
        task.join();
    }

    private static void concurrentDemo() throws Exception {
        System.out.println("\nRunning concurrent issue test on B102 (1 copy)...");
        ConcurrentIssueTask t1 = new ConcurrentIssueTask(library, "M001", "B102", Thread.MAX_PRIORITY);
        ConcurrentIssueTask t2 = new ConcurrentIssueTask(library, "M002", "B102", Thread.NORM_PRIORITY);
        t1.start(); t2.start();
        t1.join(); t2.join();
        AvailabilityWaitTask waiter = new AvailabilityWaitTask(library, "B102");
        waiter.start();
        Thread.sleep(500);
        Loan active = library.findActiveLoanForBook("B102");
        if (active != null) {
            library.returnBook(active.getMemberId(), active.getBookId(), LocalDate.now());
            System.out.println("Main thread returned B102; notifyAll() released waiting task.");
        }
        waiter.join();
        System.out.println("Concurrent issue test completed. Inventory remains consistent.");
    }

    private static void utilizationReport() { library.printUtilizationReport(); }

    private static void reservationQueue() {
        System.out.print("Book ID: ");
        library.printReservationQueue(scanner.nextLine());
    }

    private static void loadSampleData() {
        try {
            library.registerMember(new StudentMember("M001", "Aarav", "aarav@example.com"));
            library.registerMember(new FacultyMember("M002", "Dr. Meera", "meera@example.com"));
            library.registerMember(new StudentMember("M003", "Riya", "riya@example.com"));
            library.getInventory().addBook(new Book("B101", "Java Programming", "Herbert Schildt", "Programming", 3));
            library.getInventory().addBook(new Book("B102", "Data Structures", "Mark Allen Weiss", "Computer Science", 1));
            library.getInventory().addBook(new Book("B103", "Database Systems", "Raghu Ramakrishnan", "Database", 2));
            library.getInventory().addBook(new Book("B104", "Computer Networks", "Andrew S. Tanenbaum", "Networking", 2));
            library.getInventory().addBook(new Book("B105", "Operating Systems", "Silberschatz", "Systems", 2));
        } catch (Exception e) {
            System.out.println("Sample data loading error: " + e.getMessage());
        }
    }
}
