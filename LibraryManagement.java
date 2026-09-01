import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private boolean issued;

    Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    int getId() {
        return id;
    }

    void issueBook() {
        if (!issued) {
            issued = true;
            System.out.println("Book Issued");
        } else {
            System.out.println("Already Issued");
        }
    }

    void returnBook() {
        if (issued) {
            issued = false;
            System.out.println("Book Returned");
        } else {
            System.out.println("Book Not Issued");
        }
    }

    void display() {
        System.out.println("ID : " + id);
        System.out.println("Title : " + title);
        System.out.println("Author : " + author);
        System.out.println("Price : " + price);
        System.out.println("Status : " + (issued ? "Issued" : "Available"));
        System.out.println();
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Book[] b = new Book[10];
        int n = 0, ch;

        do {
            System.out.println("1.Add Book");
            System.out.println("2.Display Books");
            System.out.println("3.Search Book");
            System.out.println("4.Issue Book");
            System.out.println("5.Return Book");
            System.out.println("6.Exit");
            System.out.print("Enter Choice: ");
            ch = sc.nextInt();

            switch (ch) {

                case 1:
                    System.out.print("Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Title: ");
                    String title = sc.nextLine();

                    System.out.print("Author: ");
                    String author = sc.nextLine();

                    System.out.print("Price: ");
                    double price = sc.nextDouble();

                    b[n++] = new Book(id, title, author, price);
                    break;

                case 2:
                    for (int i = 0; i < n; i++)
                        b[i].display();
                    break;

                case 3:
                    System.out.print("Enter Book ID: ");
                    int sid = sc.nextInt();

                    for (int i = 0; i < n; i++)
                        if (b[i].getId() == sid)
                            b[i].display();
                    break;

                case 4:
                    System.out.print("Enter Book ID: ");
                    sid = sc.nextInt();

                    for (int i = 0; i < n; i++)
                        if (b[i].getId() == sid)
                            b[i].issueBook();
                    break;

                case 5:
                    System.out.print("Enter Book ID: ");
                    sid = sc.nextInt();

                    for (int i = 0; i < n; i++)
                        if (b[i].getId() == sid)
                            b[i].returnBook();
                    break;

                case 6:
                    System.out.println("Thank You");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (ch != 6);

        sc.close();
    }
}