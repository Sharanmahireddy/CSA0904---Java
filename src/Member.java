import java.util.ArrayList;
import java.util.List;

abstract class Member {
    private final String memberId;
    private String name;
    private String email;
    private final List<String> borrowedBookIds = new ArrayList<>();

    protected Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getBorrowedBookIds() { return new ArrayList<>(borrowedBookIds); }
    public void addBorrowedBook(String bookId) { borrowedBookIds.add(bookId); }
    public void removeBorrowedBook(String bookId) { borrowedBookIds.remove(bookId); }
    public boolean hasBorrowed(String bookId) { return borrowedBookIds.contains(bookId); }

    public abstract int getBorrowLimit();
    public abstract double getFinePerDay();
    public abstract String getMembershipType();

    @Override
    public String toString() {
        return memberId + " | " + name + " | " + membershipTypeLine();
    }

    private String membershipTypeLine() {
        return getMembershipType() + " | Borrowed: " + borrowedBookIds.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Member other)) return false;
        return memberId.equalsIgnoreCase(other.memberId);
    }

    @Override
    public int hashCode() { return memberId.toLowerCase().hashCode(); }
}
