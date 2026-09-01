class InvalidMemberException extends Exception {
    public InvalidMemberException(String message) { super(message); }
}

class DuplicateReservationException extends Exception {
    public DuplicateReservationException(String message) { super(message); }
}

class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) { super(message); }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) { super(message); }
}
