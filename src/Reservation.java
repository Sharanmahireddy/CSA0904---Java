import java.time.LocalDateTime;

class Reservation {
    private final String reservationId;
    private final String memberId;
    private final String bookId;
    private final LocalDateTime reservedAt;

    public Reservation(String reservationId, String memberId, String bookId) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.reservedAt = LocalDateTime.now();
    }

    public String getReservationId() { return reservationId; }
    public String getMemberId() { return memberId; }
    public String getBookId() { return bookId; }
    public LocalDateTime getReservedAt() { return reservedAt; }

    @Override
    public String toString() {
        return reservationId + " | Member: " + memberId + " | Book: " + bookId + " | " + reservedAt;
    }
}
