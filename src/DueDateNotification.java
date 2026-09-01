class DueDateNotification extends Notification {
    public DueDateNotification(String memberId, String message) {
        super(memberId, message);
    }

    @Override
    public void send() {
        System.out.println("[DUE-DATE] Member " + memberId + ": " + message);
    }
}
